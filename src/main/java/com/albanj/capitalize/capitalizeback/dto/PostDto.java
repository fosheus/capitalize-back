package com.albanj.capitalize.capitalizeback.dto;

import com.albanj.capitalize.capitalizeback.entity.ApplicationUser;
import com.albanj.capitalize.capitalizeback.entity.File;
import com.albanj.capitalize.capitalizeback.entity.Tag;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostDto implements Serializable {

    private Integer id;
    private String title;
    private String text;
    private LocalDateTime validationDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean validated;
    private UserDto owner;
    private UserDto validator;
    private List<TagDto> tags = new ArrayList<>();
    private List<FileDto> files = new ArrayList<>();

}
