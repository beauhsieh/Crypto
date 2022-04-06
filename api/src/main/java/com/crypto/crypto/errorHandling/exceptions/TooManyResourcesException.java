package com.crypto.crypto.errorHandling.exceptions;

import com.crypto.crypto.errorHandling.abs.AbstractException;
import com.crypto.crypto.errorHandling.BaseException;

import java.io.Serializable;

public class TooManyResourcesException extends AbstractException implements BaseException,Serializable {
	private static final long serialVersionUID = 1L;
	private static final String error = "tooManyResources";
	private static final String description = "The number of requests exceeds the limit.";
	
	public TooManyResourcesException() {
		super(error, description, description);
	}
	
	public TooManyResourcesException(String msg) {
		super(error, description,msg);
	}

	public TooManyResourcesException(String msg, Throwable t) {
		super(error, description, msg, t);
	}

}
