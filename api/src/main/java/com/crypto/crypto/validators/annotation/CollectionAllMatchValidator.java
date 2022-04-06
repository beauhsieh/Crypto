package com.crypto.crypto.validators.annotation;

import org.apache.commons.collections4.CollectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionAllMatchValidator implements ConstraintValidator<ValidateObjectAllMatch, Collection<?>> {
	
	  List<String> valueList = null;

	  @Override
	  public boolean isValid(Collection<?> collect, ConstraintValidatorContext context) {
		if (CollectionUtils.isNotEmpty(collect)) {
			boolean result = collect.stream().allMatch(value -> {
				return valueList.contains(value.toString().toUpperCase());
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
	  public void initialize(ValidateObjectAllMatch constraintAnnotation) {
		  valueList = new ArrayList<String>();
		  String[] split = constraintAnnotation.acceptedValues().split(",");
	        for(String val : split) {
	            valueList.add(val.toUpperCase());
	        }
	  }
}