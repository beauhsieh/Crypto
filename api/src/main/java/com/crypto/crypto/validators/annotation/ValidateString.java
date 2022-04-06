package com.crypto.crypto.validators.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StringValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateString {

    String[] acceptedValues();

    String message() default "{accept.value.error}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
