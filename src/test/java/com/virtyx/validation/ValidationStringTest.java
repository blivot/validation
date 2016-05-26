package com.virtyx.validation;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.virtyx.exception.ValidationError;

public class ValidationStringTest {

	private ValidationString target;
	
	private Container c;

	@Before
	public void setup() {
		target = new ValidationString(null);
		c = new Container();
	}

	@Test
	public void testMinValid() throws Exception {
		target.min(5);
		List<ValidationError> errors = target.validateValue("key", "testing", c);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testMinInvalid() throws Exception {
		target.min(5);
		
		List<ValidationError> errors = target.validateValue("key", "hi", c);
		assertEquals(1, errors.size());
		
		ValidationError error = errors.get(0);
		assertEquals("key", error.getKey());
		assertEquals(
				"The string 'hi' needs to be at least 5 characters long",
				error.getMessage()
		);
	}
	
	@Test
	public void testMinNotString() throws Exception {
		target.min(0);
		List<ValidationError> errors = target.validateValue("k", 10, c);
		assertEquals(1, errors.size());
		
		ValidationError error = errors.get(0);
		assertEquals(
				"Failed to convert the value of 'k' into a String",
				error.getMessage()
		);
	}
	
	@Test
	public void testMinBadInput() throws Exception {
		target.min(1);
		List<ValidationError> errors = target.validateValue("k", new Object(), c);
		assertEquals(1, errors.size());
		
		ValidationError error = errors.get(0);
		assertEquals("k", error.getKey());
		assertEquals(
				"Failed to convert the value of 'k' into a String",
				error.getMessage()
		);
	}
	
	@Test
	public void testMaxValid() throws Exception {
		target.max(5);
		List<ValidationError> errors = target.validateValue("key", "okay", c);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testMaxInvalid() throws Exception {
		target.max(5);
		
		List<ValidationError> errors = target.validateValue("key", "longstring", c);
		assertEquals(1, errors.size());
		
		ValidationError error = errors.get(0);
		assertEquals("key", error.getKey());
		assertEquals(
				"The string 'longstring' needs to be less than or equal to 5 characters long",
				error.getMessage()
		);
	}
	
	@Test
	public void testMaxConvertToString() throws Exception {
		target.max(3).convertToString();
		List<ValidationError> errors = target.validateValue("key", 100, c);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testMinValidOutOfOrder() throws Exception {
		target.valid("thisisokay").min(5);
		List<ValidationError> errors = target.validateValue("key", "thisisokay", c);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testEmail() throws Exception {
		target.email();
		List<ValidationError> errors = target.validateValue("key", "ethan@example.com", c);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testBadEmail() throws Exception {
		target.email();
		List<ValidationError> errors = target.validateValue("key", "test", c);
		assertEquals(1, errors.size());
		
		ValidationError error = errors.get(0);
		assertEquals("key", error.getKey());
		assertEquals(
				"The string 'test' is not a valid email",
				error.getMessage()
		);
	}
	
}
