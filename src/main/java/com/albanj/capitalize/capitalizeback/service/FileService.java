package com.albanj.capitalize.capitalizeback.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import com.albanj.capitalize.capitalizeback.dto.FileDto;
import com.albanj.capitalize.capitalizeback.entity.File;
import com.albanj.capitalize.capitalizeback.entity.Post;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeBadRequestException;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeGenericException;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeInternalException;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeNotFoundException;

public interface FileService {

    String getFileTextContent(Integer id) throws IOException, CapitalizeGenericException;

    byte[] getFileBinaryContent(Integer id)
            throws IOException, CapitalizeBadRequestException, CapitalizeNotFoundException;

    FileDto updateFileContent(String text, MultipartFile multipartFile, FileDto fileDto, Post post)
            throws CapitalizeInternalException, CapitalizeNotFoundException, CapitalizeBadRequestException;

    void updateFileContentText(String text, File file) throws CapitalizeInternalException;

    void updateFileContentBytes(MultipartFile multipartFile, File file) throws CapitalizeInternalException;

    File createFile(FileDto file, Post post) throws CapitalizeBadRequestException;

    File getFileById(Integer fileId) throws CapitalizeNotFoundException;

    void deleteFile(File file);

    String getFileSavePath();
}
