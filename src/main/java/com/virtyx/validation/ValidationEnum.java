package com.virtyx.validation;

import java.security.InvalidParameterException;
import java.util.function.Function;

import com.virtyx.converter.EnumFromIntConverter;

public class ValidationEnum<E extends Enum<E>> extends ValidationAny<Enum<?>, ValidationEnum<E>> {
	
	private Class<E> clazz;
	
	public ValidationEnum(Validation<?> parent, Class<E> clazz) {
		super(parent);
		if (!clazz.isEnum()) {
			throw new InvalidParameterException(
					String.format("Class %s is not an enum", clazz.getSimpleName())
			);
		}
		this.clazz = clazz;
	}
	
	@Override
	protected ValidationEnum<E> getThis() {
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public ValidationEnum<E> fromInt(Function<Integer, E> method) {
		this.converter = new EnumFromIntConverter((Function<Integer, Enum<?>>) method);
		return this;
	}
	
	public ValidationEnum<E> fromString(Function<String, E> method) {
		
		return this;
	}

	public Class<E> getClazz() {
		return clazz;
	}
	

}
