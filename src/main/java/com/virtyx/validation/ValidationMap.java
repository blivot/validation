package com.virtyx.validation;

import java.util.Map;

/**
 * This class will eventually allow for another validation schema
 * to be dropped in and can validate entire sub maps.
 * 
 * @author ethanmick
 *
 */
public class ValidationMap extends ValidationAny<Map<?, ?>, ValidationMap> {
	
	public ValidationMap(Validation<?> parent) {
		super(parent);
	}
	
	@Override
	protected ValidationMap getThis() {
		return this;
	}

}
