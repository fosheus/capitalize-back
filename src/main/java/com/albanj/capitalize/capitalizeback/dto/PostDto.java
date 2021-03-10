package com.albanj.capitalize.capitalizeback.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostDto {

    private Integer id;
    private String title;
    private String description;
    private LocalDateTime validationDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean validated;
    private UserDto owner;
    private UserDto validator;
    private List<TagDto> tags = new ArrayList<>();
    private List<FileDto> files = new ArrayList<>();

}
