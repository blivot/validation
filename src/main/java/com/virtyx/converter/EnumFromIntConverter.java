package com.virtyx.converter;

import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.virtyx.exception.ConvertException;

public class EnumFromIntConverter implements Converter<Enum<?>> {
	
	final protected Logger log = LogManager.getLogger();
	
	private Function<Integer, Enum<?>> method;
	
	public EnumFromIntConverter(Function<Integer, Enum<?>> method) {
		this.method = method;
	}
	
	public Enum<?> convert(Object obj) throws ConvertException {
		log.debug("COVERTING: "+ obj);
		log.debug("COVERTING: "+ method);
		if (
				obj instanceof Integer
		) {
			return this.method.apply((Integer)obj);
		}
		throw new ConvertException("Enum");
	}
}
