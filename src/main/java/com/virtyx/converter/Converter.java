package com.virtyx.converter;

import com.virtyx.exception.ConvertException;

public interface Converter <T> {
	
	public T convert(Object o) throws ConvertException;

}
