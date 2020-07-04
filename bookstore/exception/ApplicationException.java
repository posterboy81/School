/**
 * Project: Assignment 01
 * File: ApplicationExceptions.java
 * Date: May 30, 2019
 * Time: 12:25:37 p.m.
 *
 * @author Matthew Simpson
 */

package ca.bcit.comp2613.bookstore.exception;

/**
 * Supplied with Assignment 1 Start
 * @author Sam Cirka, A00123456
 *
 */
@SuppressWarnings("serial")
public class ApplicationException extends Exception {

	/**
	 * Construct an ApplicationException
	 * 
	 * @param message
	 *            the exception message.
	 */
	public ApplicationException(String message) {
		super(message);
	}

	/**
	 * Construct an ApplicationException
	 * 
	 * @param throwable
	 *            a Throwable.
	 */
	public ApplicationException(Throwable throwable) {
		super(throwable);
	}

}
