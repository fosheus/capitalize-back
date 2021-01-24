package com.albanj.capitalize.capitalizeback.validator.Profile;

import com.albanj.capitalize.capitalizeback.dto.ProfileDto;
import com.albanj.capitalize.capitalizeback.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProfileValidator implements ConstraintValidator<ProfileValidation, ProfileDto> {

    private final ProfileService service;

    @Autowired
    public ProfileValidator(ProfileService service) {
        this.service = service;
    }

    @Override
    public boolean isValid(ProfileDto profileDto, ConstraintValidatorContext constraintValidatorContext) {
        if (profileDto == null) {
            return false;
        }
        ProfileDto dto = service.getOneById(profileDto.getId());
        if (dto == null) {
            return false;
        }
        return dto.getLabel().equals(profileDto.getLabel());
    }
}
