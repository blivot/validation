package com.virtyx.validation;

import com.virtyx.constraint.StringConstraint;
import com.virtyx.converter.DefaultStringConverter;

public class ValidationString extends ValidationAny<String> {
	
	public ValidationString(Validation<?> parent) {
		super(parent);
	}
	
	public ValidationString min(Integer min) {
		this.constraints.add(new StringConstraint.Min(min));
		return this;
	}
	
	public ValidationString max(Integer max) {
		this.constraints.add(new StringConstraint.Max(max));
		return this;
	}
	
	public ValidationString convertToString() {
		this.converter = new DefaultStringConverter();
		return this;
	}
}
