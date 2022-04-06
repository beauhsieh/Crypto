package com.crypto.crypto.errorHandling.base;

import com.crypto.crypto.errorHandling.BaseException;
import com.crypto.crypto.errorHandling.ErrorHandlingConstants.ERROR_BUNDLE;
import com.crypto.crypto.errorHandling.abs.AbstractException;

public class ResourceNotFoundException extends AbstractException implements BaseException {
	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(ERROR_BUNDLE errorBundle) {
		super(errorBundle.name(), errorBundle.getDesc(), "");
	}
	
	public ResourceNotFoundException(ERROR_BUNDLE errorBundle, String msg) {
		super(errorBundle.name(), errorBundle.getDesc() ,msg);
	}

	public ResourceNotFoundException(ERROR_BUNDLE errorBundle, String msg, Throwable t) {
		super(errorBundle.name(), errorBundle.getDesc(), msg, t);
	}

}
