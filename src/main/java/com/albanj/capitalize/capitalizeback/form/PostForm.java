package com.albanj.capitalize.capitalizeback.form;

import com.albanj.capitalize.capitalizeback.dto.FileDto;
import com.albanj.capitalize.capitalizeback.dto.TagDto;
import com.albanj.capitalize.capitalizeback.validator.File.FileDtoValidation;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostForm {

    private Integer id;
    private String title;
    private String description;
    private List<TagDto> tags = new ArrayList<>();
    private List<@FileDtoValidation FileDto> files = new ArrayList<>();
}
