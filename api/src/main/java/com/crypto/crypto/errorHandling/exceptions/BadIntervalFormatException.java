package com.crypto.crypto.errorHandling.exceptions;

import com.crypto.crypto.errorHandling.BaseException;
import com.crypto.crypto.errorHandling.abs.AbstractException;

import java.io.Serializable;

public class BadIntervalFormatException extends AbstractException implements BaseException,Serializable {
	private static final long serialVersionUID = 1L;
	private static final String error = "badInterval";
	private static final String description = "Bad interval format";
	
	public BadIntervalFormatException() {
		super(error, description, description);
	}
	
	public BadIntervalFormatException(String msg) {
		super(error, description,msg);
	}

	public BadIntervalFormatException(String msg, Throwable t) {
		super(error, description, msg, t);
	}

}
