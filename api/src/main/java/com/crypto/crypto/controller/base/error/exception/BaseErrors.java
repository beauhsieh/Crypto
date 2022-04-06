package com.crypto.crypto.controller.base.error.exception;

import java.util.List;

public interface BaseErrors<T> {
	T getId();
	
	String getCode();
	
	List<ExceptionResponseVO> getErrors();
	
	void setErrors(List<ExceptionResponseVO> errors);
	
}
