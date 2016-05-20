package com.virtyx.annotation;

import com.virtyx.validation.Validation;

@SuppressWarnings("rawtypes")
public class ValidateJsonDefaultResolver implements ValidateJsonDefault {

	@Override
	public Validation<?> getValidation() {
		return new Validation();
	}

}
