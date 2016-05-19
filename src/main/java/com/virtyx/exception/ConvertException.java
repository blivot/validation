package com.virtyx.exception;

public class ConvertException extends ValidationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5066608011622609348L;
	
	public ConvertException(ValidationError err) {
		super(err);
	}
	
	public ConvertException(String message) {
		super(message);
	}
	
}
