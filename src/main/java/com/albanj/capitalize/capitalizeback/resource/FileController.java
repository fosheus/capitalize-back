package com.albanj.capitalize.capitalizeback.resource;

import com.albanj.capitalize.capitalizeback.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/{id}/text")
    public String getFileTextContent(Authentication authentication, @PathVariable Integer id) throws IOException {
        return fileService.getFileTextContent(id);
    }

    @GetMapping(value= "/{id}/binary",  produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getFileBinaryContent(Authentication authentication, @PathVariable Integer id) throws IOException {
        return fileService.getFileBinaryContent(id);
    }

    @PutMapping("/{id}/{path}")
    public void updateFile(Authentication authentication, @PathVariable Integer id, @PathVariable Integer fileId,@PathVariable String path, @RequestParam MultipartFile file) {
         fileService.updateFile(authentication,file,fileId,path);
    }
}
