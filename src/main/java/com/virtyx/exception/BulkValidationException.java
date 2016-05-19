package com.virtyx.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BulkValidationException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private List<ValidationError> errors = new ArrayList<ValidationError>();
	
	public BulkValidationException(List<ValidationError> errors) {
		super();
		this.errors = errors;
	}

	public List<ValidationError> getErrors() {
		return errors;
	}

	public void setErrors(List<ValidationError> errors) {
		this.errors = errors;
	}
	
	public List<String> getMessages() {
		if (this.errors == null) return null;
		
		return errors.stream().map( e->e.getMessage()).collect(Collectors.toList());
	}

}
