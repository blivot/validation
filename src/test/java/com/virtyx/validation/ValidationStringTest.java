package com.virtyx.validation;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.virtyx.exception.ValidationError;

public class ValidationStringTest {

	private ValidationString target;

	@Before
	public void setup() {
		target = new ValidationString(null);
	}

	@Test
	public void testMinValid() throws Exception {
		target.min(5);
		List<ValidationError> errors = target.validateValue("key", "testing");
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testMinInValid() throws Exception {
		target.min(5);
		
		List<ValidationError> errors = target.validateValue("key", "hi");
		assertEquals(1, errors.size());
		
		ValidationError error = errors.get(0);
		assertEquals("key", error.getKey());
		assertEquals(
				"The string 'hi' needs to be at least 5 characters long.",
				error.getMessage()
		);
	}
	
	@Test
	public void testMinNotString() throws Exception {
		target.min(0);
		List<ValidationError> errors = target.validateValue("k", 10);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testMinBadInput() throws Exception {
		target.min(1);
		List<ValidationError> errors = target.validateValue("k", new Object());
		assertEquals(1, errors.size());
		
		ValidationError error = errors.get(0);
		assertEquals("k", error.getKey());
		assertEquals(
				"Failed to convert the input into a String",
				error.getMessage()
		);
	}
}
