package com.albanj.capitalize.capitalizeback.service.impl;

import com.albanj.capitalize.capitalizeback.dto.FileDto;
import com.albanj.capitalize.capitalizeback.entity.File;
import com.albanj.capitalize.capitalizeback.entity.Post;
import com.albanj.capitalize.capitalizeback.enums.FileTypeEnum;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeBadRequestException;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeNotFoundException;
import com.albanj.capitalize.capitalizeback.mapper.FileMapper;
import com.albanj.capitalize.capitalizeback.repository.FileRepository;
import com.albanj.capitalize.capitalizeback.service.FileService;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class FileServiceImpl implements FileService {

    private final FileRepository repo;
    private final String FILE_PATH;

    @Autowired
    public FileServiceImpl(FileRepository repo, Environment env) {
        this.repo = repo;
        FILE_PATH = env.getProperty("capitalize.files.path");

    }

    @Override
    public String getFileSavePath() {
        return FILE_PATH;
    }

    @Override
    public String getFileTextContent(Integer id) throws IOException {

        File file = repo.findById(id).orElseThrow(() -> new CapitalizeNotFoundException());

        if (!file.getType().equals(FileTypeEnum.TEXT.name())) {
            throw new CapitalizeBadRequestException();
        }

        return Files.readString(Paths.get(file.getFullPath()));
    }

    @Override
    public byte[] getFileBinaryContent(Integer id) throws IOException {
        File file = repo.findById(id).orElseThrow(() -> new CapitalizeNotFoundException());

        if (!file.getType().equals(FileTypeEnum.OTHER.name())) {
            throw new CapitalizeBadRequestException();
        }
        return Files.readAllBytes(Paths.get(file.getFullPath()));
    }

    @Override
    public void updateFileContentText(String text, File file) {
        try {
            Path path = null;
            try {
                path = Paths.get(file.getFullPath());
            } catch (InvalidPathException e) {
                throw new CapitalizeBadRequestException("Path of the file is not correctly formated");
            }
            Files.deleteIfExists(path);
            createFileSystemDirectories(file.getFullPath());
            Files.writeString(path, text);
        } catch (Exception e) {
            log.error("Error saving file text", e);
            throw new CapitalizeBadRequestException(
                    "Impossible to save the file to the location [" + file.getPath() + "]");
        }
    }

    @Override
    public void updateFileContentBytes(MultipartFile multipartFile, File file) {

        byte[] bytes = null;
        Path path = null;
        try {
            if (multipartFile == null) {
                throw new CapitalizeBadRequestException("multipartFile element is empty");
            }
            bytes = multipartFile.getBytes();
            try {
                path = Paths.get(file.getFullPath());
            } catch (InvalidPathException e) {
                throw new CapitalizeBadRequestException("Path of the file is not correctly formated");
            }
            Files.deleteIfExists(path);
            createFileSystemDirectories(file.getFullPath());
            java.io.File systemFile = new java.io.File(file.getFullPath());
            try (FileOutputStream stream = new FileOutputStream(systemFile)) {
                stream.write(bytes);
            }
        } catch (Exception e) {
            log.error("Error while saving multipart file locally", e);
            throw new CapitalizeBadRequestException(
                    "Impossible to save the file to the location [" + file.getPath() + "]");
        }
    }

    @Override
    public void deleteFiles(List<File> files) {
        this.repo.deleteAll(files);
        for (File file : files) {
            try {
                java.io.File systemFile = new java.io.File(file.getFullPath());
                if (systemFile != null) {
                    systemFile.delete();
                }
            } catch (Exception ignore) {
            }
        }

    }

    @Override
    public File createFile(FileDto file, Post post) {
        File fileEntity = FileMapper.map(file, post, FILE_PATH);
        List<File> filesWithSamePath = this.repo.findByFullPath(fileEntity.getFullPath());
        if (filesWithSamePath.size() != 0) {
            throw new CapitalizeBadRequestException();
        }
        return this.repo.save(fileEntity);
    }

    @Override
    public FileDto updateFileContent(String text, MultipartFile multipartFile, FileDto fileDto, Post post) {

        File fileEntity = null;
        if (fileDto.getId() == null) {
            fileEntity = this.createFile(fileDto, post);
        } else {
            fileEntity = this.repo.findById(fileDto.getId()).orElseThrow(() -> new CapitalizeNotFoundException());
        }
        if (fileEntity.getType().equals(FileTypeEnum.TEXT.name())) {
            updateFileContentText(text, fileEntity);
        } else if (fileEntity.getType().equals(FileTypeEnum.OTHER.name())) {
            updateFileContentBytes(multipartFile, fileEntity);
        } else {
            throw new CapitalizeBadRequestException("FileType [" + fileEntity.getType() + "] unknown");
        }

        return FileMapper.map(fileEntity);

    }

    @Override
    public File getFileById(Integer fileId) {
        return this.repo.findById(fileId).orElseThrow(() -> new CapitalizeNotFoundException());
    }

    private void createFileSystemDirectories(String filename) {

        Path path = Paths.get(filename);
        String directoryName = path.getParent().toString();

        java.io.File directory = new java.io.File(directoryName);
        if (!directory.exists()) {
            directory.mkdirs();
        }

    }
}
