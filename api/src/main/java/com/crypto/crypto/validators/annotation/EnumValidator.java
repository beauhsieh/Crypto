package com.crypto.crypto.validators.annotation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EnumValidator implements ConstraintValidator<ValidateEnum, String> {
	
	  List<String> valueList = null;

	  @Override
	  public boolean isValid(String value, ConstraintValidatorContext context) {
	    if(!StringUtils.isEmpty(value) && !valueList.contains(value.toUpperCase())) {
	    	//TODO : not good...
	    	String text =  valueList.stream().collect(Collectors.joining(",")).toLowerCase();
	    	String msg = "must match one of the values in the list " + text + "";
	    	context.disableDefaultConstraintViolation();
	    	ConstraintViolationBuilder builder = context.buildConstraintViolationWithTemplate(msg);
	    	builder.addConstraintViolation();
	      return false;
	    }
	    return true;
	  }

	  @Override
	  public void initialize(ValidateEnum constraintAnnotation) {
	    valueList = new ArrayList<String>();
	    Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClazz();
	    
	    @SuppressWarnings("rawtypes")
	    Enum[] enumValArr = enumClass.getEnumConstants();
	    for(@SuppressWarnings("rawtypes")
	    Enum enumVal : enumValArr) {
	      valueList.add(enumVal.toString().toUpperCase());
	    }
	  }
	}