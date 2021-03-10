package com.albanj.capitalize.capitalizeback.dto;

import lombok.Data;

@Data
public class TagDto {

    private Integer id;
    private String label;
    /*
     * @TagTypeValidation(groups = ValidationOnRequest.class) private TagTypeDto
     * type;
     */
}
