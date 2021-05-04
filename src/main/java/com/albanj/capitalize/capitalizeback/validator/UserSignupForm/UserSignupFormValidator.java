package com.albanj.capitalize.capitalizeback.validator.UserSignupForm;

import com.albanj.capitalize.capitalizeback.form.UserSignupForm;
import com.albanj.capitalize.capitalizeback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserSignupFormValidator implements ConstraintValidator<UserSignupFormValidation, UserSignupForm> {

    private final UserService userService;

    @Autowired
    public UserSignupFormValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(UserSignupForm userSignupForm, ConstraintValidatorContext context) {
        return userService.getOneByEmailOrUsername(userSignupForm.getEmail(), userSignupForm.getUsername()) == null;
    }
}
