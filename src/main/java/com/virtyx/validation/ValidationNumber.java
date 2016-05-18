package com.virtyx.validation;

import com.virtyx.constraint.StringConstraint;


public class ValidationNumber extends ValidationAny {

	public ValidationNumber(Validation parent) {
		super(parent);
	}

	public ValidationNumber min(Integer min) {
		return this;
	}
}
