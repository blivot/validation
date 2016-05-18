package com.virtyx.validation;

import com.virtyx.constraint.AnyConstraint;

public class ValidationAny extends Validation <Object> {
	
	public ValidationAny() {

	}

	public ValidationAny(Validation parent) {
		this.parent = parent;
	}

	public ValidationAny required() {
		this.constraints.add(new AnyConstraint.Required());
		return this;
	}

}
