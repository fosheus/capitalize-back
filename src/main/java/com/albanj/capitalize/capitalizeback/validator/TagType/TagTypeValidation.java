package com.albanj.capitalize.capitalizeback.validator.TagType;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Documented
@Constraint(validatedBy = { TagTypeValidator.class })
@Retention(RetentionPolicy.RUNTIME)
public @interface TagTypeValidation {

    String message() default "TagType invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
