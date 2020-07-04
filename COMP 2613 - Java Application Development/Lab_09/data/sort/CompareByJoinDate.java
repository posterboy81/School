/**
 * Project: COMP2613 - 09 - Lab 9
 * File: CompareByJoinDate.java
 * Date: May 21, 2019
 * Time: 11:59:51 p.m.
 *
 * @author Matthew Simpson / A00820997
 */

package lab09.data.sort;

import java.time.LocalDate;
import java.util.Comparator;

import lab09.data.Customer;


public class CompareByJoinDate implements Comparator<Customer> {

	/**
	 * Method to compare two LocalDate objects.
	 */
	@Override
	public int compare(final Customer numberOne, final Customer numberTwo) {
		final LocalDate c1 = numberOne.getJoinDate();
		final LocalDate c2 = numberTwo.getJoinDate();
		return c1.compareTo(c2);

	}


}
