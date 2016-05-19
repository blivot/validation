package com.virtyx.validation;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;

import com.virtyx.exception.ValidationError;

//@SuppressWarnings("unchecked")
public class ValidationEnumTest {
	
	private ValidationEnum<Planet> target;
	
	private Container c;

	@Before
	public void setup() {
		target = new ValidationEnum<Planet>(null, Planet.class);
		c = new Container();
	}

	@Test
	public void testConvertToEnum() throws Exception {
		target.fromInt(new Function<Integer, Planet>() {
			@Override
			public Planet apply(Integer t) {
				return Planet.values()[t];
			}
		}).valid(Planet.EARTH); //THE ONE TRUE PLANET
		
		List<ValidationError> errors = target.validateValue("key", 2, c);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testConvertToEnumLambda() throws Exception {
		target.fromInt( i-> Planet.values()[i] ).valid(Planet.EARTH); //THE ONE TRUE PLANET
		
		List<ValidationError> errors = target.validateValue("key", 2, c);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testValidAndConvert() throws Exception {
		target.valid(Planet.EARTH).fromInt( (i)-> Planet.values()[i]);
		
		List<ValidationError> errors = target.validateValue("key", 2, c);
		assertEquals(0, errors.size());
	}
	
	private enum Planet {
		MERCURY, VENUS, EARTH, MARS, JUPITER, SATURN, URANUS, NEPTUNE
	}
	

}
