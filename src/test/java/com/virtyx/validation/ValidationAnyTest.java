package com.virtyx.validation;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.virtyx.exception.ValidationError;

public class ValidationAnyTest {
	
	private ValidationAny<Object, ValidationAny<?, ?>> target;

	@Before
	public void setup() {
		target = new ValidationAny<Object, ValidationAny<?, ?>>(null);
	}

	@Test
	public void testValid() throws Exception {
		target.valid("okay");
		System.out.println("HERE 1");
		List<ValidationError> errors = target.validateValue("key", "okay");
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testValid2() throws Exception {
		target.valid(10);
		List<ValidationError> errors = target.validateValue("key", 10);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testInvalid1() throws Exception {
		target.valid("okay");
		List<ValidationError> errors = target.validateValue("key", "okayy");
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testInvalid2() throws Exception {
		target.valid("okay");
		List<ValidationError> errors = target.validateValue("key", 2);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testInvalid3() throws Exception {
		target.valid(10);
		List<ValidationError> errors = target.validateValue("key", 2);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testInvalid4() throws Exception {
		target.valid(1);
		List<ValidationError> errors = target.validateValue("key", "1");
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testInvalid5() throws Exception {
		target.valid(new Boolean(true));
		List<ValidationError> errors = target.validateValue("key", "1");
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testForbidden() throws Exception {
		target.forbidden();
		List<ValidationError> errors = target.validateValue("key", null);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testForbiddenInvalid() throws Exception {
		target.forbidden();
		List<ValidationError> errors = target.validateValue("key", "something");
		assertEquals(1, errors.size());
	}

}
