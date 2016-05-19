package com.virtyx.annotation;

import com.virtyx.validation.Validation;

public class ValidateJsonDefault<C> {
	
	public Validation<C> getValidation() {
		Validation<C> v = new Validation<C>(null);
		return v;
	}
	

}
