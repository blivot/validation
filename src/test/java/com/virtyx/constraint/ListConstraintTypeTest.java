package com.virtyx.constraint;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ListConstraintTypeTest {
	
	@SuppressWarnings("serial")
	@Test
	public void testType() throws Exception {
		List<String> strings = new ArrayList<String>() {{
			add("test");
		}};
		
		assertTrue(
				(new ListConstraint.Type(String.class)).valid(strings)
		);
	}
	
	@SuppressWarnings("serial")
	@Test
	public void testTypeWrong() throws Exception {
		List<Integer> numbers = new ArrayList<Integer>() {{
			add(1);
		}};
		
		assertFalse(
				(new ListConstraint.Type(String.class)).valid(numbers)
		);
	}


}
