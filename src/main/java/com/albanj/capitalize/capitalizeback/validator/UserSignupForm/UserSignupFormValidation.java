package com.albanj.capitalize.capitalizeback.validator.UserSignupForm;

import com.albanj.capitalize.capitalizeback.validator.ProfileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Documented
@Constraint(validatedBy = {UserSignupFormValidator.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserSignupFormValidation {

    String message() default "Profile does not exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}