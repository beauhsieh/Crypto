package com.crypto.crypto.validators.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@ReportAsSingleViolation
/**
 * Enum must be simple enum, and every element should be upper case.
 * */
public @interface ValidateEnum {

  Class<? extends Enum<?>> enumClazz();

  String message() default "{accept.value.error}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}