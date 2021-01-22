package com.albanj.capitalize.capitalizeback.validator;

import com.albanj.capitalize.capitalizeback.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProfileValidator implements ConstraintValidator<ProfileValidation,String> {

    private final ProfileService profileService;

    @Autowired
    public ProfileValidator(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Override
    public boolean isValid(String profileLabel, ConstraintValidatorContext context) {
        return profileService.getOneByLabel(profileLabel) != null;
    }
}
