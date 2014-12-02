package com.android.pennaed.test.contacts.phoneNumber;

import android.provider.ContactsContract;

import com.android.pennaed.contacts.PhoneNumber;

import junit.framework.TestCase;

/**
 * Created by sruthi on 12/2/14.
 */
public class ConvertToNumTest extends TestCase {
	public ConvertToNumTest() {
		super();
	}

	public void testNull() {
		long num = PhoneNumber.convertToNum(null);
		assertNotNull(num);
	}
	public void testSingleChar() {
		long num = PhoneNumber.convertToNum("a");
		assertEquals(2L, num);
	}
	public void testNineValidChars() {
		long num = PhoneNumber.convertToNum("aabbeeffm");
		assertEquals(222233336, num);
	}
	public void testInvalidChars() {
		long num = PhoneNumber.convertToNum("abd-ef.");
		assertEquals(22333, num);
	}
	public void testValidChars() {
		long num = PhoneNumber.convertToNum("jjnnijiedx");
		assertEquals(5566454339L, num);
	}
}
