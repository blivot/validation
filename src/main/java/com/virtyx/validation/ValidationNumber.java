package com.virtyx.validation;

public class ValidationNumber extends ValidationAny<Number, ValidationNumber> {

	public ValidationNumber(Validation<?> parent) {
		super(parent);
	}
	
	@Override
	protected ValidationNumber getThis() {
		return this;
	}

	public ValidationNumber min(Integer min) {
		return this;
	}
}
