package com.virtyx.constraint;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringConstraintEmailTest {
	
	@Test
	public void testEmail1() throws Exception {
		assertFalse(
				(new StringConstraint.Email()).valid("")
				);
	}
	
	@Test
	public void testEmail2() throws Exception {
		assertFalse(
				(new StringConstraint.Email()).valid("test")
				);
	}
	
	@Test
	public void testEmail3() throws Exception {
		assertFalse(
				(new StringConstraint.Email()).valid("test@")
				);
	}
	
	@Test
	public void testEmail4() throws Exception {
		assertTrue(
				(new StringConstraint.Email()).valid("test@test")
				);
	}
	
	@Test
	public void testEmail5() throws Exception {
		assertTrue(
				(new StringConstraint.Email()).valid("test@example.com")
				);
	}
	
	@Test
	public void testEmail6() throws Exception {
		assertFalse(
				(new StringConstraint.Email()).valid("Ethan test@test.com")
				);
	}

}
