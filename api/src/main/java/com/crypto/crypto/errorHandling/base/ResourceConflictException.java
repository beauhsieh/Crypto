package com.crypto.crypto.errorHandling.base;

import com.crypto.crypto.errorHandling.ErrorHandlingConstants;
import com.crypto.crypto.errorHandling.abs.AbstractException;
import com.crypto.crypto.errorHandling.BaseException;

public class ResourceConflictException extends AbstractException implements BaseException {
	private static final long serialVersionUID = 1L;

	public ResourceConflictException(ErrorHandlingConstants.ERROR_BUNDLE errorBundle) {
		super(errorBundle.name(), errorBundle.getDesc(), "");
	}

	public ResourceConflictException(ErrorHandlingConstants.ERROR_BUNDLE errorBundle, String msg) {
		super(errorBundle.name(), errorBundle.getDesc(), msg);
	}

	public ResourceConflictException(ErrorHandlingConstants.ERROR_BUNDLE errorBundle, String msg, Throwable t) {
		super(errorBundle.name(), errorBundle.getDesc(), msg, t);
	}

}
