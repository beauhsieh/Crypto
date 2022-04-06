package com.crypto.crypto.validators;

import org.springframework.stereotype.Component;

@Component
public class IntegerAndCommaValidator extends RegexValidator{
	private static final String INTEGER_AND_COMMA_PATTERN = "^(\\d+(,\\d+)*)?$";

		public IntegerAndCommaValidator() {
			super(INTEGER_AND_COMMA_PATTERN);
		}

}
