package com.virtyx.integration;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.virtyx.exception.ValidationError;
import com.virtyx.validation.Validation;

public class CompleteTest {
	
	final protected Logger log = LogManager.getLogger();

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
			.property("name").string().min(0).required();

		json.put("id", 3);

		List<ValidationError> errors = target.validate(json);
		assertEquals(1, errors.size());
		
		ValidationError error = errors.get(0);
		assertEquals(
				"'name' is required",
				error.getMessage()
		);
	}

	@Test
	public void testDenyUnknown() throws Exception {
		target
			.property("id").number().min(0)
			.property("name").string().optional();
		
		json.put("id", 3);
		json.put("random", "key");

		List<ValidationError> errors = target.validate(json);
		assertEquals(1, errors.size());
		
		ValidationError error = errors.get(0);
		assertEquals(
				"Key 'random' was sent but is unnecessary",
				error.getMessage()
		);
	}
	
	@Test
	public void testAllowUnknown() throws Exception {
		target
			.property("id").number().min(0)
			.property("name").string().optional();
		
		target.setAllowUnknown(true);
		
		json.put("id", 3);
		json.put("random", "key");

		List<ValidationError> errors = target.validate(json);
		assertEquals(0, errors.size());
	}

	/**
	 * A Test Class
	 * 
	 * @author ethanmick
	 *
	 */
	@SuppressWarnings("unused")
	private static class Animal {

		private int id;
		
		private String name;

		public Animal() {

		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}
}
