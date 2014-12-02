package com.android.pennaed.test.contacts.phoneNumber;

import com.android.pennaed.contacts.PhoneNumber;

import junit.framework.Test;
import junit.framework.TestCase;

/**
 * Created by sruthi on 12/2/14.
 */
//tests only run for smaller case letters
public class TestConvertLetterToDigit extends TestCase{
	public TestConvertLetterToDigit() {
		super();
	}


	public void testInvalidInput() {
		boolean  exceptionThrown = false;
		try {
			char digit = PhoneNumber.convertLetterToDigit('?');
		} catch (IllegalArgumentException e) {
			exceptionThrown = true;
		}

		assertTrue(exceptionThrown);
	}

	public void testB() {
		char digit = PhoneNumber.convertLetterToDigit('b');
		assertEquals('2', digit);
	}

	public void testF() {
		char digit = PhoneNumber.convertLetterToDigit('f');
		assertEquals('3', digit);
	}

	public void testI() {
		char digit = PhoneNumber.convertLetterToDigit('i');
		assertEquals('4', digit);
	}

	public void testL() {
		char digit = PhoneNumber.convertLetterToDigit('l');
		assertEquals('5', digit);
	}

	public void testN()  {
		char digit = PhoneNumber.convertLetterToDigit('n');
		assertEquals('6', digit);
	}

	public void testQ() {
		char digit = PhoneNumber.convertLetterToDigit('q');
		assertEquals('7', digit);
	}

	public void testU() {
		char digit = PhoneNumber.convertLetterToDigit('u');
		assertEquals('8', digit);
	}

	public void testX() {
		char digit = PhoneNumber.convertLetterToDigit('x');
		assertEquals('9', digit);
	}
}

