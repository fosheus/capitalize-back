package com.albanj.capitalize.capitalizeback.validator.Profile;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Documented
@Constraint(validatedBy = { ProfileValidator.class })
@Retention(RetentionPolicy.RUNTIME)
public @interface ProfileValidation {

    String message() default "Profile invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
