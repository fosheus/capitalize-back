package com.albanj.capitalize.capitalizeback.mapper;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.albanj.capitalize.capitalizeback.dto.FileDto;
import com.albanj.capitalize.capitalizeback.entity.File;
import com.albanj.capitalize.capitalizeback.entity.Post;

import org.springframework.util.CollectionUtils;

public class FileMapper {

    public static FileDto map(File file) {
        if (file == null)
            return null;
        FileDto dto = new FileDto();
        dto.setId(file.getId());
        dto.setName(file.getName());
        dto.setPath(file.getPath());
        dto.setType(file.getType());
        return dto;
    }

    public static File map(FileDto dto, Post post, String fileLocation) {
        if (dto == null || fileLocation == null)
            return null;
        File file = new File();
        file.setId(dto.getId());
        file.setName(dto.getName());
        file.setPath(dto.getPath());
        file.setType(dto.getType());
        if (post != null && post.getId() != null) {
            file.setFullPath(Paths.get(fileLocation, String.valueOf(post.getId()), dto.getPath()).toString());
        }
        file.setPost(post);
        return file;
    }

    public static Set<File> map(List<FileDto> dtos, Post post, String fileLocation) {
        if (CollectionUtils.isEmpty(dtos) || post == null || post.getId() == null || fileLocation == null)
            return Collections.emptySet();
        return dtos.stream().map(dto -> FileMapper.map(dto, post, fileLocation)).collect(Collectors.toSet());
    }

    public static List<FileDto> map(Set<File> files) {
        List<FileDto> dtos = new ArrayList<>();
        if (CollectionUtils.isEmpty(files))
            return dtos;
        for (File file : files) {
            dtos.add(FileMapper.map(file));
        }
        return dtos;
    }
}
