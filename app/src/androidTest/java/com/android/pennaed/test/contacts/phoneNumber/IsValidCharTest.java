package com.android.pennaed.test.contacts.phoneNumber;

import com.android.pennaed.contacts.PhoneNumber;

import junit.framework.TestCase;

/**
 * Created by sruthi on 12/2/14.
 */
public class IsValidCharTest extends TestCase {
	public IsValidCharTest() {
		super();
	}

	public void testValidFirstChar() {
		boolean result = PhoneNumber.isValidChar('5', 0);
		assertEquals(true, result);
	}

	public void testInvalidFirstChar() {
		boolean result = PhoneNumber.isValidChar('0', 0);
		assertEquals(false, result);
	}

	public void testChar() {
		boolean result = PhoneNumber.isValidChar('4', 4);
		assertEquals(true, result);
	}

	public void testInvalidChar() {
		boolean result = PhoneNumber.isValidChar('-', 4);
		assertEquals(false, result);
	}
}
