package com.albanj.capitalize.capitalizeback.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Documented
@Constraint(validatedBy = {ProfileValidator.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProfileValidation {

    String message() default "Profile does not exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
