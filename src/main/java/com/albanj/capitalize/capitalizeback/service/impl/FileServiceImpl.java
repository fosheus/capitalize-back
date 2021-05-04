package com.albanj.capitalize.capitalizeback.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;

import com.albanj.capitalize.capitalizeback.dto.FileDto;
import com.albanj.capitalize.capitalizeback.entity.File;
import com.albanj.capitalize.capitalizeback.entity.Post;
import com.albanj.capitalize.capitalizeback.enums.CapitalizeErrorEnum;
import com.albanj.capitalize.capitalizeback.enums.FileTypeEnum;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeBadRequestException;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeGenericException;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeInternalException;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeNotFoundException;
import com.albanj.capitalize.capitalizeback.mapper.FileMapper;
import com.albanj.capitalize.capitalizeback.repository.FileRepository;
import com.albanj.capitalize.capitalizeback.service.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

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
    public String getFileTextContent(Integer id) throws IOException, CapitalizeGenericException {

        if (id == null) {
            throw new CapitalizeBadRequestException(CapitalizeErrorEnum.INVALID_ARGUMENT.code,
                    MessageFormat.format(CapitalizeErrorEnum.INVALID_ARGUMENT.text, "file.id", "null"),
                    "FileServiceImpl::getFileTextContent id NULL");
        }
        File file = repo.findById(id)
                .orElseThrow(() -> new CapitalizeNotFoundException(CapitalizeErrorEnum.FILE_NOT_FOUND.code,
                        MessageFormat.format(CapitalizeErrorEnum.FILE_NOT_FOUND.text, id), MessageFormat.format(
                                "FileServiceImpl::getFileTextContent File id={0} does not exist in database", id)));

        if (!file.getType().equals(FileTypeEnum.TEXT.name())) {
            throw new CapitalizeBadRequestException(CapitalizeErrorEnum.FILE_TYPE_INVALID.code,
                    MessageFormat.format(CapitalizeErrorEnum.FILE_TYPE_INVALID.text, file.getType(), "TEXT"),
                    MessageFormat.format(
                            "FileServiceImpl::getFileTextContent trying to get text content of non text file | file.id={0};file.type={1}",
                            file.getId(), file.getType()));
        }

        String content = "";
        try {
            content = Files.readString(Paths.get(file.getFullPath()));
        } catch (Exception ignore) {
            log.warn("getFileTextContent : File [{}] of id [{}] for post [{}] is missing in the file system",
                    file.getFullPath(), id, file.getPost().getId());
        }
        return content;
    }

    @Override
    public byte[] getFileBinaryContent(Integer id)
            throws IOException, CapitalizeBadRequestException, CapitalizeNotFoundException {

        if (id == null) {
            throw new CapitalizeBadRequestException(CapitalizeErrorEnum.INVALID_ARGUMENT.code,
                    MessageFormat.format(CapitalizeErrorEnum.INVALID_ARGUMENT.text, "file.id", "null"),
                    "FileServiceImpl::getFileBinaryContent id NULL");
        }
        File file = repo.findById(id)
                .orElseThrow(() -> new CapitalizeNotFoundException(CapitalizeErrorEnum.FILE_NOT_FOUND.code,
                        MessageFormat.format(CapitalizeErrorEnum.FILE_NOT_FOUND.text, id), MessageFormat.format(
                                "FileServiceImpl::getFileBinaryContent File id={0} does not exist in database", id)));

        if (!file.getType().equals(FileTypeEnum.OTHER.name())) {
            throw new CapitalizeBadRequestException(CapitalizeErrorEnum.FILE_TYPE_INVALID.code,
                    MessageFormat.format(CapitalizeErrorEnum.FILE_TYPE_INVALID.text, file.getType(), "OTHER"),
                    MessageFormat.format(
                            "FileServiceImpl::getFileTextContent trying to get binary content of non binary file | file.id={0};file.type={1}",
                            file.getId(), file.getType()));
        }

        byte[] byteArray = null;
        try {
            byteArray = Files.readAllBytes(Paths.get(file.getFullPath()));
        } catch (Exception ignore) {
            log.warn("getFileBinaryContent : File [{}] of id [{}] for post [{}] is missing in the file system",
                    file.getFullPath(), id, file.getPost().getId());
        }
        return byteArray;
    }

    @Override
    public FileDto updateFileContent(String text, MultipartFile multipartFile, FileDto fileDto, Post post)
            throws CapitalizeInternalException, CapitalizeNotFoundException, CapitalizeBadRequestException {

        File fileEntity = null;
        if (fileDto.getId() == null) {
            fileEntity = this.createFile(fileDto, post);
        } else {
            fileEntity = repo.findById(fileDto.getId())
                    .orElseThrow(() -> new CapitalizeNotFoundException(CapitalizeErrorEnum.FILE_NOT_FOUND.code,
                            MessageFormat.format(CapitalizeErrorEnum.FILE_NOT_FOUND.text, fileDto.getId()),
                            MessageFormat.format(
                                    "FileServiceImpl::updateFileContent File id={0} does not exist in database",
                                    fileDto.getId())));
        }
        if (fileEntity.getType().equals(FileTypeEnum.TEXT.name())) {
            updateFileContentText(text, fileEntity);
        } else if (fileEntity.getType().equals(FileTypeEnum.OTHER.name())) {
            updateFileContentBytes(multipartFile, fileEntity);
        } else {
            throw new CapitalizeInternalException("FileType [" + fileEntity.getType() + "] unknown");
        }

        return FileMapper.map(fileEntity);

    }

    @Override
    public void updateFileContentText(String text, File file) throws CapitalizeInternalException {
        try {
            Path path = null;
            try {
                path = Paths.get(file.getFullPath());
            } catch (InvalidPathException e) {
                throw new CapitalizeBadRequestException(CapitalizeErrorEnum.FORMAT_ERROR_POST_FILE_PATH.code,
                        MessageFormat.format(CapitalizeErrorEnum.FORMAT_ERROR_POST_FILE_PATH.text, file.getPath()),
                        MessageFormat.format(
                                "FileServiceImpl::updateFileContentText Path of the file is not correctly formated | path={0}",
                                file.getFullPath()));
            }
            Files.deleteIfExists(path);
            createFileSystemDirectories(file.getFullPath());
            Files.writeString(path, text);
        } catch (Exception e) {
            throw new CapitalizeInternalException(
                    "FileServiceImpl::updateFileContentText Impossible to save the file to the location ["
                            + file.getPath() + "]");
        }
    }

    @Override
    public void updateFileContentBytes(MultipartFile multipartFile, File file) throws CapitalizeInternalException {

        byte[] bytes = null;
        Path path = null;
        try {
            if (multipartFile == null) {
                throw new CapitalizeBadRequestException(CapitalizeErrorEnum.EMPTY_UPLOADED_FILE.code,
                        MessageFormat.format(CapitalizeErrorEnum.EMPTY_UPLOADED_FILE.text, file.getPath()),
                        "FileServiceImpl::updateFileContentBytes multipartFile element is empty");
            }
            bytes = multipartFile.getBytes();
            try {
                path = Paths.get(file.getFullPath());
            } catch (InvalidPathException e) {
                throw new CapitalizeBadRequestException(CapitalizeErrorEnum.FORMAT_ERROR_POST_FILE_PATH.code,
                        MessageFormat.format(CapitalizeErrorEnum.FORMAT_ERROR_POST_FILE_PATH.text, file.getPath()),
                        MessageFormat.format(
                                "FileServiceImpl::updateFileContentBytes Path of the file is not correctly formated | path={0}",
                                file.getFullPath()));
            }
            Files.deleteIfExists(path);
            createFileSystemDirectories(file.getFullPath());
            java.io.File systemFile = new java.io.File(file.getFullPath());
            try (FileOutputStream stream = new FileOutputStream(systemFile)) {
                stream.write(bytes);
            }
        } catch (Exception e) {
            throw new CapitalizeInternalException(
                    "FileServiceImpl::updateFileContentBytes Impossible to save the file to the location ["
                            + file.getPath() + "]");
        }
    }

    @Override
    public void deleteFile(File file) {
        this.repo.delete(file);
        try {
            java.io.File systemFile = new java.io.File(file.getFullPath());
            if (systemFile != null) {
                systemFile.delete();
            }
        } catch (Exception e) {
            log.warn("FileServiceImpl::deleteFile file deletion error", e);
        }

    }

    @Override
    public File createFile(FileDto file, Post post) throws CapitalizeBadRequestException {
        File fileEntity = FileMapper.map(file, post, FILE_PATH);
        List<File> filesWithSamePath = this.repo.findByFullPath(fileEntity.getFullPath());
        if (filesWithSamePath.size() != 0) {
            throw new CapitalizeBadRequestException(CapitalizeErrorEnum.EXISTING_FILE_WITH_SAME_PATH.code,
                    MessageFormat.format(CapitalizeErrorEnum.EXISTING_FILE_WITH_SAME_PATH.text, fileEntity.getPath()),
                    "FileServiceImpl::createFile existing file with same path " + fileEntity.getFullPath());
        }
        return this.repo.save(fileEntity);
    }

    @Override
    public File getFileById(Integer fileId) throws CapitalizeNotFoundException {
        return repo.findById(fileId).orElseThrow(() -> new CapitalizeNotFoundException(
                CapitalizeErrorEnum.FILE_NOT_FOUND.code,
                MessageFormat.format(CapitalizeErrorEnum.FILE_NOT_FOUND.text, fileId),
                MessageFormat.format("FileServiceImpl::getFileById File id={0} does not exist in database", fileId)));
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
