package com.crypto.crypto.validators.annotation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;


public class StringValidator implements ConstraintValidator<ValidateString, String> {

    private List<String> valueList;

    @Override
    public void initialize(ValidateString constraintAnnotation) {
        valueList = new ArrayList<String>();
        for(String val : constraintAnnotation.acceptedValues()) {
            valueList.add(val.toUpperCase());
        }
    }
    /**
     * allow null
     * */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(!StringUtils.isEmpty(value) && !valueList.contains(value.toUpperCase())) {
            return false;
        }
        return true;
    }

}
