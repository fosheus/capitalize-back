package com.albanj.capitalize.capitalizeback.dto;

import com.albanj.capitalize.capitalizeback.validator.TagType.TagTypeValidation;
import com.albanj.capitalize.capitalizeback.validator.groups.ValidationOnRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class TagDto implements Serializable {

    private Integer id;
    private String label;
   /* @TagTypeValidation(groups = ValidationOnRequest.class)
    private TagTypeDto type;*/
}
