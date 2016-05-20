package com.virtyx.validation;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ValidationPropertyTest {
	
	private ValidationProperty target;
	
	private Container c;

	@Before
	public void setup() {
		target = new ValidationProperty(null, "test");
		c = new Container();
	}
	
	// This should compile
	@Test
	public void testCreateEnum() throws Exception {
		target.enumm(Planet.class).fromInt( i-> Planet.values()[i]);
		target.validate("key", 3, c);
		Planet mars = (Planet) c.object;
		assertEquals(Planet.MARS, mars);
	}
	
	
	
	private enum Planet {
		MERCURY, VENUS, EARTH, MARS, JUPITER, SATURN, URANUS, NEPTUNE
	}

}
