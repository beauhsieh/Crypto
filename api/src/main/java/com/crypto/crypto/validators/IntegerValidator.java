package com.crypto.crypto.validators;

import org.springframework.stereotype.Component;

@Component
public class IntegerValidator extends RegexValidator{
	private static final String INTEGER_PATTERN ="\\d+";

		public IntegerValidator() {
			super(INTEGER_PATTERN);
		}
	
}
