package com.virtyx.constraint;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringConstraintMinTest {
	
	@Test
	public void testMin0() throws Exception {
		assertTrue(
				(new StringConstraint.Min(0)).valid("")
				);
	}
	
	@Test
	public void testMin1() throws Exception {
		assertFalse(
				(new StringConstraint.Min(1)).valid("")
				);
	}
	
	@Test
	public void testMinN1() throws Exception {
		assertTrue(
				(new StringConstraint.Min(-1)).valid("")
				);
	}

}
