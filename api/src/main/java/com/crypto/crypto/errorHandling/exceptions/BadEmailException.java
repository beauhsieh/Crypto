package com.crypto.crypto.errorHandling.exceptions;

import com.crypto.crypto.errorHandling.abs.AbstractException;
import com.crypto.crypto.errorHandling.BaseException;

public class BadEmailException extends AbstractException implements BaseException {
	private static final long serialVersionUID = 1L;
	private static final String error = "badEmail";
	private static final String description = "Email format is invalid";

	public BadEmailException() {
		super(error, description, description);
	}

	public BadEmailException(String msg) {
		super(error, description, msg);
	}

	public BadEmailException(String msg, Throwable t) {
		super(error, description, msg, t);
	}

}
