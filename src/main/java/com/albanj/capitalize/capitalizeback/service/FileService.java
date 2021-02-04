package com.albanj.capitalize.capitalizeback.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    String getFileTextContent(Integer id) throws IOException;

    byte[] getFileBinaryContent(Integer id) throws IOException;

    void updateFile(Authentication authentication, MultipartFile file,Integer fileId, String filePath);
}
