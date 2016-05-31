package com.virtyx.validation;

import java.util.List;

import com.virtyx.constraint.ListConstraint;

public class ValidationList<E> extends ValidationAny<List<?>, ValidationList<E>> {
	
	private Class<E> clazz;

	public ValidationList(Validation<?> parent, Class<E> clazz) {
		super(parent);
		this.clazz = clazz;
		this.addConstraint(new ListConstraint.Type(this.clazz));
	}
	
	@Override
	protected ValidationList<E> getThis() {
		return this;
	}

	public Class<E> getClazz() {
		return clazz;
	}

}
