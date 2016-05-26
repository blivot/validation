package com.virtyx.constraint;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringConstraintMaxTest {
	
	@Test
	public void testMax0() throws Exception {
		assertTrue(
				(new StringConstraint.Max(0)).valid("")
				);
	}
	
	@Test
	public void testMax1() throws Exception {
		assertTrue(
				(new StringConstraint.Max(0)).valid("")
				);
	}
	
	@Test
	public void testMax2() throws Exception {
		assertFalse(
				(new StringConstraint.Max(0)).valid("test")
				);
	}

}
