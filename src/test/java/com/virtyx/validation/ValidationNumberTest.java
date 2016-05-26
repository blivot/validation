package com.virtyx.validation;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.virtyx.exception.ValidationError;

public class ValidationNumberTest {
	
	private ValidationNumber target;
	
	private Container c;

	@Before
	public void setup() {
		target = new ValidationNumber(null);
		c = new Container();
	}
	
	@Test
	public void testMinValid() throws Exception {
		target.min(5);
		List<ValidationError> errors = target.validateValue("key", 10, c);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testMinInvalid() throws Exception {
		target.min(5);
		List<ValidationError> errors = target.validateValue("key", 3, c);
		assertEquals(1, errors.size());
		
		ValidationError error = errors.get(0);
		assertEquals("key", error.getKey());
		assertEquals(
				"The number 3 cannot be less than 5",
				error.getMessage()
		);
	}
	
	@Test
	public void testMinInvalidDouble() throws Exception {
		target.min(5.5);
		List<ValidationError> errors = target.validateValue("key", 2.8, c);
		assertEquals(1, errors.size());
		
		ValidationError error = errors.get(0);
		assertEquals("key", error.getKey());
		assertEquals(
				"The number 2.8 cannot be less than 5.5",
				error.getMessage()
		);
	}
	
	@Test
	public void testMaxValid() throws Exception {
		target.max(5);
		List<ValidationError> errors = target.validateValue("key", 2, c);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testMaxInvalid() throws Exception {
		target.max(5);
		List<ValidationError> errors = target.validateValue("key", 10, c);
		assertEquals(1, errors.size());
		
		ValidationError error = errors.get(0);
		assertEquals("key", error.getKey());
		assertEquals(
				"The number 10 cannot be greater than 5",
				error.getMessage()
		);
	}
	
	@Test
	public void testMaxInvalidDouble() throws Exception {
		target.max(5.5);
		List<ValidationError> errors = target.validateValue("key", 7.6, c);
		assertEquals(1, errors.size());
		
		ValidationError error = errors.get(0);
		assertEquals("key", error.getKey());
		assertEquals(
				"The number 7.6 cannot be greater than 5.5",
				error.getMessage()
		);
	}

}
