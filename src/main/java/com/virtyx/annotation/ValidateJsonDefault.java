package com.virtyx.annotation;

import com.virtyx.validation.Validation;

public interface ValidateJsonDefault<C> {
	
	public Validation<C> getValidation();
}
