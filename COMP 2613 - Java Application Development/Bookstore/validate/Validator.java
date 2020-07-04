/**
 * Project: Assignment 01
 * File: Validators.java
 * Date: May 30, 2019
 * Time: 12:25:37 p.m.
 *
 * @author Matthew Simpson
 */

package ca.bcit.comp2613.bookstore.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Validator {


	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


	/**
	 * Private constructor to prevent instantiation
	 */
	private Validator() {
	}

	/**
	 * Method to check an email address is valid. If the email is valid, return said email. If it is not valid, return "n/a"
	 *
	 * @param checkEmail
	 *            the email address to be checked.
	 */
	public static boolean checkEmail(final String checkEmail) {
		boolean good = false;
		if (!checkEmail.isEmpty() && checkEmail != null) {
			Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(checkEmail);
	        good = matcher.find();
		}
		return good;
	}
	
	/**
	 * Method to check a strings length and shorten it to the specified length. 
	 * @param checkString - the string to check 
	 * @param length - the specified length
	 * @return shortString - the shortened string
	 */
	public static String shortenString(final String checkString, final int length) {
		String shortString = checkString;
		if(checkString.length() > length) {
			int shortLength = length -3;
			shortString = String.valueOf(checkString.substring(0, shortLength) + "...");
		}
	
		return shortString;
	}


}