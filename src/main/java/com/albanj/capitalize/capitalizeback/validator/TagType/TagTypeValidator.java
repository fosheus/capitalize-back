package com.albanj.capitalize.capitalizeback.validator.TagType;

import com.albanj.capitalize.capitalizeback.dto.TagTypeDto;
import com.albanj.capitalize.capitalizeback.repository.TagTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TagTypeValidator implements ConstraintValidator<TagTypeValidation, TagTypeDto> {

    private final TagTypeRepository repo;

    @Autowired
    public TagTypeValidator(TagTypeRepository repo) {
        this.repo = repo;
    }

    @Override
    public boolean isValid(TagTypeDto tagTypeDto, ConstraintValidatorContext constraintValidatorContext) {
        if (tagTypeDto == null) return false;
        return repo.findById(tagTypeDto.getId()).isPresent();
    }
}
