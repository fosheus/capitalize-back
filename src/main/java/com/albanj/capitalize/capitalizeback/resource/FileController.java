package com.albanj.capitalize.capitalizeback.resource;

import com.albanj.capitalize.capitalizeback.dto.FileContentDto;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeBadRequestException;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeGenericException;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeNotFoundException;
import com.albanj.capitalize.capitalizeback.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public FileContentDto getFileTextContent(Authentication authentication, @PathVariable Integer id)
            throws IOException, CapitalizeGenericException {

        return new FileContentDto(fileService.getFileTextContent(id));
    }

    @GetMapping(value = "/{id}/binary", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getFileBinaryContent(Authentication authentication, @PathVariable Integer id)
            throws IOException, CapitalizeBadRequestException, CapitalizeNotFoundException {
        byte[] array = fileService.getFileBinaryContent(id);
        return array;
    }
}
