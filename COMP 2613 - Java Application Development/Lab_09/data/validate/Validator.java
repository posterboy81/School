/**
 * Project: COMP2613 - 09 - Lab 09
 * File: Validator.java
 * Date: May 21, 2019
 * Time: 11:59:51 p.m.
 *
 * @author Matthew Simpson / A00820997
 */

package lab09.data.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {


	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	/**
	 * Private Constructor. 
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
			Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(checkEmail);
	        good = matcher.find();
		}
		return good;
	}


}
