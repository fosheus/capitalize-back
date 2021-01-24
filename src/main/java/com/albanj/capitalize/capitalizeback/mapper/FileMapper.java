package com.albanj.capitalize.capitalizeback.mapper;

import com.albanj.capitalize.capitalizeback.dto.FileDto;
import com.albanj.capitalize.capitalizeback.entity.File;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class FileMapper {

    public static FileDto map(File file) throws IOException {
        if( file == null) return null;
        FileDto dto = new FileDto();
        dto.setId(file.getId());
        dto.setContent(Files.readString(Path.of(file.getFullPath())));
        dto.setName(file.getName());
        dto.setPath(file.getPath());
        dto.setType(file.getType());
        return dto;
    }

    public static File map(FileDto dto,Integer postId,String fileLocation) {
        if (dto == null || postId == null || fileLocation == null) return null;
        File file = new File();
        file.setId(dto.getId());
        file.setName(dto.getName());
        file.setPath(dto.getPath());
        file.setType(dto.getType());
        file.setFullPath(Paths.get(fileLocation,String.valueOf(postId),dto.getPath()).toString());
        return file;
    }

    public static Set<File> map(List<FileDto> dtos,Integer postId, String fileLocation) {
        if (CollectionUtils.isEmpty(dtos) || postId == null || fileLocation == null) return Collections.emptySet();
        return dtos.stream().map(dto->FileMapper.map(dto,postId,fileLocation)).collect(Collectors.toSet());
    }

    public static List<FileDto> map(Set<File> files) throws IOException {
        List<FileDto> dtos=new ArrayList<>();
        if (CollectionUtils.isEmpty(files)) return dtos;
        for (File file : files) {
            dtos.add(FileMapper.map(file));
        }
        return dtos;
    }
}
