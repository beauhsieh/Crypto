package com.crypto.crypto.errorHandling.abs;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public abstract class AbstractException extends RuntimeException implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String error;
	private String description;
	
	public AbstractException() {
		super();
	}
	
	public AbstractException(String error, String description, String msg) {
		super(msg);
		this.error = error;
		this.description = description;
	}

	public AbstractException(String error, String description, String msg, Throwable t) {
		super(msg, t);
		this.error = error;
		this.description = description;
	}

	public String getError() {
		return error;
	}

	public String getDescription() {
		
		return StringUtils.isEmpty(getMessage()) ? description : description + " [" + getMessage() + "]";
	}
	
}
