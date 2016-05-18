package com.virtyx.exception;

public class ValidationError {
	
	private String key;
	
	private Object value;
	
	private String message;

	public ValidationError() {
		this(null, null, null);
	}

	public ValidationError(String key, Object value, String message) {
		this.key = key;
		this.value = value;
		this.message = message;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
