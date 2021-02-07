package com.albanj.capitalize.capitalizeback.service.impl;

import com.albanj.capitalize.capitalizeback.entity.File;
import com.albanj.capitalize.capitalizeback.enums.FileTypeEnum;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeBadRequestException;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeNotFoundException;
import com.albanj.capitalize.capitalizeback.repository.FileRepository;
import com.albanj.capitalize.capitalizeback.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository repo;

    @Autowired
    public FileServiceImpl(FileRepository repo) {
        this.repo = repo;
    }

    @Override
    public String getFileTextContent(Integer id) throws IOException {

        Optional<File> optionalFile = repo.findById(id);
        if (optionalFile.isEmpty()) {
            throw new CapitalizeNotFoundException();
        }
        File file = optionalFile.get();
        if (!file.getType().equals(FileTypeEnum.TEXT.name())) {
            throw new CapitalizeBadRequestException();
        }

        return Files.readString(Paths.get(file.getFullPath()));
    }

    @Override
    public byte[] getFileBinaryContent(Integer id) throws IOException {
        Optional<File> optionalFile = repo.findById(id);
        if (optionalFile.isEmpty()) {
            throw new CapitalizeNotFoundException();
        }
        File file = optionalFile.get();
        if (!file.getType().equals(FileTypeEnum.BINARY.name())) {
            throw new CapitalizeBadRequestException();
        }
        return Files.readAllBytes(Paths.get(file.getFullPath()));
    }

    @Override
    public void updateFile(Authentication authentication, MultipartFile file, Integer fileId, String filePath) {

    }
}
