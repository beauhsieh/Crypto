package com.crypto.crypto.errorHandling;

import com.crypto.crypto.controller.base.error.exception.ExceptionResponseVO;
import com.crypto.crypto.controller.base.error.exception.ParameterException;
import com.crypto.crypto.errorHandling.base.ResourceConflictException;
import com.crypto.crypto.errorHandling.base.ResourceNotFoundException;
import com.crypto.crypto.errorHandling.base.ServiceUnavailableException;
import com.crypto.crypto.errorHandling.exceptions.*;
import com.crypto.crypto.utils.ErrorUtils;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.messaging.handler.annotation.support.MethodArgumentTypeMismatchException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.MethodNotAllowedException;


/**
 * The global error handling. Mapping HTTP Status Code with customized exceptions.
 * 
 *
 * */
@ControllerAdvice
public class GlobalExceptionHandlingControllerAdvice {

	private static final Logger logger = LoggerFactory
			.getLogger(GlobalExceptionHandlingControllerAdvice.class);
	/**
	 * Http Code : 200
	 * */
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ExceptionHandler({
			FileSizeLimitExceededException.class
	})
	ResponseEntity<ExceptionResponseVO> handleOK(Exception e) {
		return new ResponseEntity<ExceptionResponseVO>(processErrorMessage(e), HttpStatus.OK);
	}

	/**
	 * Http Code : 400
	 * */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	@ExceptionHandler({	HttpMessageNotReadableException.class,
						MissingServletRequestParameterException.class,
						UnsatisfiedServletRequestParameterException.class,
						BadDateException.class,
						BadIntervalFormatException.class,
						BeanCreationException.class,
						TooManyResourcesException.class,
						MissingPathVariableException.class,
						MethodArgumentTypeMismatchException.class,
						NumberFormatException.class,
						MethodArgumentNotValidException.class})
	ResponseEntity<ExceptionResponseVO> handleBadRequest(Exception e) {
		if (e instanceof MissingServletRequestParameterException || 
			e instanceof UnsatisfiedServletRequestParameterException ||
		 	e instanceof IllegalArgumentException ||
			e instanceof MissingPathVariableException ||
		    e instanceof MethodArgumentTypeMismatchException ||
			e instanceof NumberFormatException ||
			e instanceof MethodArgumentNotValidException) {
			e = new ParameterException(e.getMessage());
		}
		return new ResponseEntity<ExceptionResponseVO>(processErrorMessage(e), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Http Code : 404
	 * */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({	ResourceNotFoundException.class})
	@ResponseBody
	ExceptionResponseVO handleNotFound(Exception e) {
		return processErrorMessage(e);
	}

	/**
	 * Http Code : 405
	 * */
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler({	HttpRequestMethodNotSupportedException.class, MethodNotAllowedException.class})
	@ResponseBody
	ExceptionResponseVO handleMethodNotAllowed(Exception e) {
		return processErrorMessage(e);
	}
	
	/**
	 * Http Code : 409
	 * */
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler({ ResourceConflictException.class,
						DataIntegrityViolationException.class})
	@ResponseBody
	ExceptionResponseVO handleConflict(Exception e) {
		return processErrorMessage(e);
	}

	/**
	 * Http Code : 422
	 */
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler({ BadEmailException.class
	})
	@ResponseBody
	ExceptionResponseVO handleUnprocessableEntity(Exception e) {
		return processErrorMessage(e);
	}

	/**
	 * Http Code : 429
	 * */
	@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
	@ExceptionHandler({ RequestUnavailableException.class, QuotaLimitationException.class })
	@ResponseBody
	ExceptionResponseVO handleToManyRequest(Exception e) {
		return processErrorMessage(e);
	}
	
	/**
	 * Http Code : 500
	 * UnHandle Exception (Exception.class) handled by this method.
	 * */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({	Exception.class,
						NullPointerException.class})
	@ResponseBody
	ResponseEntity<ExceptionResponseVO> handleInteralServerError(Exception e) {
		Throwable cause = e.getCause();
		
		if (e instanceof InvalidFormatException || cause instanceof InvalidFormatException
		 || e instanceof IllegalArgumentException || cause instanceof NumberFormatException
		 || e instanceof MethodArgumentNotValidException) {
			return handleBadRequest(e);
		}
		return new ResponseEntity<ExceptionResponseVO>(processErrorMessage(e), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * Http Code : 503
	 * */
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	@ExceptionHandler({ServiceUnavailableException.class})
	@ResponseBody
	ResponseEntity<ExceptionResponseVO> handleServiceUnavailable(Exception e) {
		return new ResponseEntity<ExceptionResponseVO>(processErrorMessage(e), HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	private ExceptionResponseVO processErrorMessage(Exception e) {
		ExceptionResponseVO vo = new ExceptionResponseVO();
		if (e instanceof BaseException){
			vo.setError(((BaseException)e).getError());
			vo.setDescription(((BaseException)e).getDescription());
		} else if (e.getCause() instanceof InvalidFormatException || e.getCause() instanceof NumberFormatException || e.getCause() instanceof NullPointerException) {
			vo.setError(ParameterException.error);
			vo.setDescription(e.getCause().getMessage());
		} else {
			vo.setError(ErrorHandlingConstants.ERROR_BUNDLE.internalError.name());
			vo.setDescription(ErrorUtils.errorTrackString(e));
			logger.error("UnHandling Exception", e);
		}
		return vo;
	}
	
}
