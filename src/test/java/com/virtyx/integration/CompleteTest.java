package com.virtyx.integration;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.virtyx.exception.ValidationError;
import com.virtyx.validation.Validation;

public class CompleteTest {

	private Validation<Animal> target;
	private Map<String, Object> json;

	@Before
	public void setup() {
		target = new Validation<Animal>(Animal.class);
		json = new HashMap<String, Object>();
	}

	@Test
	public void testComplexObject() throws Exception {
		target
			.property("id").number().min(0)
			.property("name").string().required();

		json.put("id", 3);

		List<ValidationError> errors = target.validate(json);
		assertEquals(1, errors.size());
		
		ValidationError error = errors.get(0);
		assertEquals(
				"'name' is required",
				error.getMessage()
		);
	}


	/**
	 * A Test Class
	 * 
	 * @author ethanmick
	 *
	 */
	private static class Animal {

		private int id;
		
		private String name;

		public Animal() {

		}

	}
}
