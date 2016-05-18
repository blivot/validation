package com.virtyx.validation;

import com.virtyx.constraint.StringConstraint;

public class ValidationString extends ValidationAny {
	
	public ValidationString(Validation parent) {
		super(parent);
	}
	
	public ValidationString min(Integer min) {
		this.constraints.add(new StringConstraint.Min(min));
		return this;
	}
}
