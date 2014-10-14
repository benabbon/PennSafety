package com.android.pennaed.campussafety;

/*
 * Class that holds helper methods for converting a string to a number
 */
public class PhoneNumber {

	public static long convertToNum(String numString) {
		String output = "";
		for (int i = 0; i < numString.length(); i++) {
			char c = numString.charAt(i);
			if (isValidChar(c, i)) {
				if (Character.isLetter(c)) {
					c = convertLetterToDigit(Character.toLowerCase(c));
				}
				output += c;
			}
		}
		return Long.parseLong(output);
	}

	/*
	 * Converts an character from the alphabet to its numeric form. Assumes
	 * input character is in lower case
	 */
	public static char convertLetterToDigit(char c) {
		switch (c) {
			case 'a':
			case 'b':
			case 'c':
				return '2';
			case 'd':
			case 'e':
			case 'f':
				return '3';
			case 'g':
			case 'h':
			case 'i':
				return '4';
			case 'j':
			case 'k':
			case 'l':
				return '5';
			case 'm':
			case 'n':
			case 'o':
				return '6';
			case 'p':
			case 'q':
			case 'r':
			case 's':
				return '7';
			case 't':
			case 'u':
			case 'v':
				return '8';
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				return '9';
			default:
				throw new IllegalArgumentException();
		}
	}

	/* Checks whether a given char in the phone number is valid */
	public static boolean isValidChar(char c, int pos) {
		if (pos == 0) {
			if (c != '0' && Character.isLetterOrDigit(c)) {
				return true;
			}
		} else {
			if (Character.isLetterOrDigit(c)) {
				return true;
			}
		}
		return false;
	}
}
