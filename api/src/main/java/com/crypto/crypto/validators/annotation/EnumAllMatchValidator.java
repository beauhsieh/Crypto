package com.crypto.crypto.validators.annotation;

import org.apache.commons.collections4.CollectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class EnumAllMatchValidator implements ConstraintValidator<ValidateEnumAllMatch, Collection<String>> {
	
	  List<String> valueList = null;

	  @Override
	  public boolean isValid(Collection<String> collect, ConstraintValidatorContext context) {
		if (CollectionUtils.isNotEmpty(collect)) {
			boolean result = collect.stream().allMatch(value -> {
				return valueList.contains(value.toUpperCase());
			});
			if (!result) {
				String text =  valueList.stream().collect(Collectors.joining(",")).toLowerCase();
		    	String msg = "must be contained in the list " + text + "";
		    	context.disableDefaultConstraintViolation();
		    	ConstraintViolationBuilder builder = context.buildConstraintViolationWithTemplate(msg);
		    	builder.addConstraintViolation();
		    	return false;
			}
		}
	    return true;
	  }

	  @Override
	  public void initialize(ValidateEnumAllMatch constraintAnnotation) {
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