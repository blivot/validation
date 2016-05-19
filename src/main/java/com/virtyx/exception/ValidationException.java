package com.virtyx.exception;

public class ValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 113655064214393748L;

	private ValidationError error;

	public ValidationException() {

	}

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(ValidationError err) {
		this.error = err;
	}

	public ValidationError getError() {
		return error;
	}

	public void setError(ValidationError error) {
		this.error = error;
	}

}
