package com.virtyx.validation;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.virtyx.exception.ValidationError;

public class ValidationListTest {
	
	final protected Logger log = LogManager.getLogger();
	
	private ValidationList<Integer> target;
	
	private Container c;

	@Before
	public void setup() {
		target = new ValidationList<Integer>(null, Integer.class);
		c = new Container();
	}

	@Test
	public void testDefaultList() throws Exception {
		List<Integer> defaultValue = new ArrayList<Integer>();
		target.setDefault(defaultValue).optional();
		
		List<ValidationError> errors = target.validateValue("key", null, c);
		
		assertEquals(0, errors.size());
		assertEquals(c.object, defaultValue);
	}

}
