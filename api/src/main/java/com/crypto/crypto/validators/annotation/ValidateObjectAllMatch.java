package com.crypto.crypto.validators.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CollectionAllMatchValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@ReportAsSingleViolation
public @interface ValidateObjectAllMatch {
	 /**number separator by comma*/
	 String acceptedValues();

	 String message() default "{accept.value.error}";

	 Class<?>[] groups() default { };

	 Class<? extends Payload>[] payload() default { };
}
