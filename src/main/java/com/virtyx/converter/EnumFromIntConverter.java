package com.virtyx.converter;

import java.util.function.Function;

import com.virtyx.exception.ConvertException;

public class EnumFromIntConverter implements Converter<Enum<?>> {
	
	private Function<Integer, Enum<?>> method;
	
	public EnumFromIntConverter(Function<Integer, Enum<?>> method) {
		this.method = method;
	}
	
	public Enum<?> convert(Object obj) throws ConvertException {
		System.out.println("COVERTING: "+ obj);
		System.out.println("COVERTING: "+ method);
		if (
				obj instanceof Integer
		) {
			return this.method.apply((Integer)obj);
		}
		throw new ConvertException("Cannot convert to Enum!");
	}
}
