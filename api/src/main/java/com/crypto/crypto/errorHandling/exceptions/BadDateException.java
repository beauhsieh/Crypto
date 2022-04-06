package com.crypto.crypto.errorHandling.exceptions;

import com.crypto.crypto.errorHandling.abs.AbstractException;
import com.crypto.crypto.errorHandling.BaseException;

public class BadDateException extends AbstractException implements BaseException {
	private static final long serialVersionUID = 1L;
	private static final String error = "badOnBoardDay";
	private static final String description = "On board day format is invalid";

	public BadDateException() {
		super(error, description, description);
	}

	public BadDateException(String msg) {
		super(error, description, msg);
	}

	public BadDateException(String msg, Throwable t) {
		super(error, description, msg, t);
	}

}
