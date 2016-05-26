package com.virtyx.validation;

import com.virtyx.constraint.StringConstraint;
import com.virtyx.converter.DefaultStringConverter;

public class ValidationString extends ValidationAny<String, ValidationString> {
	
	public ValidationString(Validation<?> parent) {
		super(parent);
	}
	
	@Override
	protected ValidationString getThis() {
		return this;
	}
	
	public ValidationString min(Integer min) {
		return addConstraint(new StringConstraint.Min(min));
	}
	
	public ValidationString max(Integer max) {
		return addConstraint(new StringConstraint.Max(max));
	}
	
	public ValidationString email() {
		return addConstraint(new StringConstraint.Email());
	}
	
	public ValidationString convertToString() {
		this.converter = new DefaultStringConverter();
		return this;
	}
}
