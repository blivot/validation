package com.virtyx.converter;

import com.virtyx.exception.ConvertException;

public class DefaultStringConverter implements Converter<String> {

	public String convert(Object obj) throws ConvertException {
		if (
				obj instanceof Integer ||
				obj instanceof String ||
				obj instanceof Boolean ||
				obj instanceof Long ||
				obj instanceof Double ||
				obj instanceof Short
		) {
			return obj.toString();
		}
		throw new ConvertException("String");
	}

}
