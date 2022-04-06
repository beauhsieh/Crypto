package com.crypto.crypto.validators;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.Validation;
import javax.validation.Validator;

public class ValidatorUtils {
	private static final Logger logger = LoggerFactory.getLogger(ValidatorUtils.class);
    private static final Validator javaxValidator = Validation.buildDefaultValidatorFactory().getValidator();
    private static final SpringValidatorAdapter validator = new SpringValidatorAdapter(javaxValidator);

    public static Errors validate(Object entry) {
        Errors errors = new BeanPropertyBindingResult(entry, entry.getClass().getName());
        validator.validate(entry, errors);
        if (errors == null || errors.getAllErrors().isEmpty())
            return null;
        else {
            logger.error(errors.toString());
            return errors;
        }
    }
}
