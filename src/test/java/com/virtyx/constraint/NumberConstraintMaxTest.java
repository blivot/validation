package com.virtyx.constraint;

import static org.junit.Assert.*;

import org.junit.Test;

public class NumberConstraintMaxTest {
	
	@Test
	public void testMax0() throws Exception {
		assertTrue(
				(new NumberConstraint.Max(0)).valid(0)
				);
	}
	
	@Test
	public void testMax1() throws Exception {
		assertFalse(
				(new NumberConstraint.Max(0)).valid(1)
				);
	}
	
	@Test
	public void testMax2() throws Exception {
		assertTrue(
				(new NumberConstraint.Max(10)).valid(5)
				);
	}

}
