package com.albanj.capitalize.capitalizeback.form;

import com.albanj.capitalize.capitalizeback.dto.FileDto;
import com.albanj.capitalize.capitalizeback.dto.TagDto;
import com.albanj.capitalize.capitalizeback.validator.File.FileDtoValidation;
import com.albanj.capitalize.capitalizeback.validator.groups.ValidationOnRequest;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostForm {

    private Integer id;
    private String title;
    private String description;
    private List<TagDto> tags = new ArrayList<>();
    private List< @FileDtoValidation(groups=ValidationOnRequest.class) FileDto> files = new ArrayList<>();
}
