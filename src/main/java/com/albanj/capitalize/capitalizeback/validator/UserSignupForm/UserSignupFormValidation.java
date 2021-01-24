package com.albanj.capitalize.capitalizeback.validator.UserSignupForm;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Documented
@Constraint(validatedBy = {UserSignupFormValidator.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserSignupFormValidation {

    String message() default "UserSignupForm invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}