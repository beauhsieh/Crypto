package com.crypto.crypto.controller.base.error.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.crypto.crypto.errorHandling.BaseException;

import java.io.Serializable;
import java.util.List;

@JsonInclude(Include.NON_NULL)
public class ExceptionResponseVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String error;
	private String description;
	private List<String> descriptions;

	public ExceptionResponseVO() {
		super();
	}

	public ExceptionResponseVO(BaseException e) {
		this.error = e.getError();
		this.description = e.getDescription();
	}

	public ExceptionResponseVO(String error, String description) {
		super();
		this.error = error;
		this.description = description;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<String> descriptions) {
		this.descriptions = descriptions;
	}

}
