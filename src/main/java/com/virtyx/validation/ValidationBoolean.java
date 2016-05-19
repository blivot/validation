package com.virtyx.validation;

public class ValidationBoolean extends ValidationAny<Boolean, ValidationBoolean> {
	
	public ValidationBoolean(Validation<?> parent) {
		super(parent);
	}
	
	@Override
	protected ValidationBoolean getThis() {
		return this;
	}
}
