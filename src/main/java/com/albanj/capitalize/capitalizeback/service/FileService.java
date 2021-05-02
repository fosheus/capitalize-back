package com.albanj.capitalize.capitalizeback.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import com.albanj.capitalize.capitalizeback.dto.FileDto;
import com.albanj.capitalize.capitalizeback.entity.File;
import com.albanj.capitalize.capitalizeback.entity.Post;

public interface FileService {

    String getFileTextContent(Integer id) throws IOException;

    byte[] getFileBinaryContent(Integer id) throws IOException;

    FileDto updateFileContent(String text, MultipartFile multipartFile, FileDto fileDto, Post post);

    void updateFileContentText(String text, File file);

    void updateFileContentBytes(MultipartFile multipartFile, File file);

    File createFile(FileDto file, Post post);

    File getFileById(Integer fileId);

    void deleteFiles(List<File> files);

    String getFileSavePath();
}
