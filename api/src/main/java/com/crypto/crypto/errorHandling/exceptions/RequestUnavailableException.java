package com.crypto.crypto.errorHandling.exceptions;

import com.crypto.crypto.errorHandling.abs.AbstractException;
import com.crypto.crypto.errorHandling.BaseException;

import java.io.Serializable;

public class RequestUnavailableException extends AbstractException implements BaseException,Serializable {
	private static final long serialVersionUID = 1L;
	private static final String error = "requestUnavailable";
	private static final String description = "Retry request later";
	
	public RequestUnavailableException() {
		super(error, description, description);
	}
	
	public RequestUnavailableException(String msg) {
		super(error, description,msg);
	}

	public RequestUnavailableException(String msg, Throwable t) {
		super(error, description, msg, t);
	}

}
