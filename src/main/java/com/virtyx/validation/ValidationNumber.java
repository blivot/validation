package com.virtyx.validation;

import com.virtyx.constraint.NumberConstraint;

public class ValidationNumber extends ValidationAny<Number, ValidationNumber> {

	public ValidationNumber(Validation<?> parent) {
		super(parent);
	}
	
	@Override
	protected ValidationNumber getThis() {
		return this;
	}

	public ValidationNumber min(Number val) {
		return addConstraint(new NumberConstraint.Min(val));
	}
	
	public ValidationNumber max(Number val) {
		return addConstraint(new NumberConstraint.Max(val));
	}
}
