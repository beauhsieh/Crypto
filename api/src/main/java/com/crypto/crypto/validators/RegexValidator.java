package com.crypto.crypto.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class RegexValidator {
	private Pattern pattern;
	private Matcher matcher;

	public RegexValidator() {
	}

	protected RegexValidator(String patternStr) {
		this.pattern = Pattern.compile(patternStr);
	}

	public boolean validate(final String hex) {

		matcher = pattern.matcher(hex);
		return matcher.matches();

	}
}
