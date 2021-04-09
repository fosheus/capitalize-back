package com.albanj.capitalize.capitalizeback.validator.File;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.TYPE_USE })
@Documented
@Constraint(validatedBy = { FileValidator.class })
@Retention(RetentionPolicy.RUNTIME)
public @interface FileDtoValidation {

    String message() default "File invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
