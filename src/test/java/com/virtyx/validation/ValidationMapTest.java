package com.virtyx.validation;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.virtyx.exception.ValidationError;

public class ValidationMapTest {
	
	final protected Logger log = LogManager.getLogger();
	
	private ValidationMap target;
	
	private Container c;
	
	@Before
	public void setup() {
		target = new ValidationMap(null);
		c = new Container();
	}
	
	@Test
	public void testDefaultMap() throws Exception {
		Map<String, Object> defaultValue = new HashMap<String, Object>();
		target.setDefault(defaultValue).optional();
		
		List<ValidationError> errors = target.validateValue("key", null, c);
		
		assertEquals(0, errors.size());
		assertEquals(c.object, defaultValue);
	}

}
