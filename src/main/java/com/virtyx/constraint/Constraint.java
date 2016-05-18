package com.virtyx.constraint;

import java.util.List;

import com.virtyx.exception.ValidationError;
import com.virtyx.exception.ValidationException;

public abstract class Constraint {
	
	public abstract List<ValidationError> validate(String key, Object toValidate) throws ValidationException;

}
