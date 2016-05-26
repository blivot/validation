package com.virtyx.constraint;

import static org.junit.Assert.*;

import org.junit.Test;

public class NumberConstraintMinTest {
	
	@Test
	public void testMin0() throws Exception {
		assertTrue(
				(new NumberConstraint.Min(0)).valid(0)
				);
	}
	
	@Test
	public void testMin1() throws Exception {
		assertFalse(
				(new NumberConstraint.Min(1)).valid(0)
				);
	}
	
	@Test
	public void testMin2() throws Exception {
		assertTrue(
				(new NumberConstraint.Min(5)).valid(10)
				);
	}

}
