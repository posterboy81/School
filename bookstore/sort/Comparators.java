/**
 * Project: Assignment 01
 * File: Comparators.java
 * Date: May 30, 2019
 * Time: 12:25:37 p.m.
 *
 * @author Matthew Simpson
 */

package ca.bcit.comp2613.bookstore.sort;

import java.time.LocalDate;
import java.util.Comparator;

import ca.bcit.comp2613.bookstore.data.Book;
import ca.bcit.comp2613.bookstore.data.Customer;
import ca.bcit.comp2613.bookstore.data.Purchase.PurchaseHistory;

/**
 * Class to hold various comparators.
 * 
 * @author Matthew Simpson
 *
 */
public class Comparators {

	public Comparators() {

	}

	/**
	 * Compare customers by join date. 
	 * 
	 * @author Matthew Simpson
	 *
	 */
	public class CompareByJoinDate implements Comparator<Customer> {

		@Override
		public int compare(Customer numberOne, Customer numberTwo) {
			final LocalDate c1 = numberOne.getJoinDate();
			final LocalDate c2 = numberTwo.getJoinDate();
			return c1.compareTo(c2);

		}

	}

	/**
	 * Compare customers by descending join date. 
	 * 
	 * @author Matthew Simpson
	 *
	 */
			
	public class CompareByJoinDateDescending implements Comparator<Customer> {

		@Override
		public int compare(Customer numberOne, Customer numberTwo) {
			final LocalDate c1 = numberOne.getJoinDate();
			final LocalDate c2 = numberTwo.getJoinDate();
			return c2.compareTo(c1);
		}

	}
	
	/**
	 * Compare customers by last name. 
	 * 
	 * @author Matthew Simpson
	 *
	 */
	public class CompareByLastName implements Comparator<Customer> {

		@Override
		public int compare(Customer o1, Customer o2) {
			String name1 = o1.getLastName();
			String name2 = o2.getLastName();

			return name1.compareTo(name2);
		}

	}

	/**
	 * Compare books by author name. 
	 * 
	 * @author Matthew Simpson
	 *
	 */
	public class CompareByAuthorName implements Comparator<Book> {
		@Override
		public int compare(Book o1, Book o2) {
			String str1 = o1.getAuthors();
			String str2 = o2.getAuthors();
			return str1.compareTo(str2);
		}

	}

	/**
	 * Compare books by descending author name. 
	 * 
	 * @author Matthew Simpson
	 *
	 */
	public class CompareByAuthorNameDescending implements Comparator<Book> {
		@Override
		public int compare(Book o1, Book o2) {
			String str1 = o1.getAuthors();
			String str2 = o2.getAuthors();
			return str2.compareTo(str1);
		}

	}

	/**
	 * Compare purchase history by last name. 
	 * 
	 * @author Matthew Simpson
	 *
	 */
	public class ComparePurchaseByLastName implements Comparator<PurchaseHistory> {

		@Override
		public int compare(PurchaseHistory o1, PurchaseHistory o2) {
			String name1 = o1.getLastName();
			String name2 = o2.getLastName();
			return name1.compareTo(name2);
		}

	}
	
	/**
	 * Compare purchase history by descending last name. 
	 * 
	 * @author Matthew Simpson
	 *
	 */
	public class ComparePurchaseByLastNameDescending implements Comparator<PurchaseHistory> {

		@Override
		public int compare(PurchaseHistory o1, PurchaseHistory o2) {
			String name1 = o1.getLastName();
			String name2 = o2.getLastName();
			return name2.compareTo(name1);
		}

	}

	/**
	 * Compare purchase history by title. 
	 * 
	 * @author Matthew Simpson
	 *
	 */
	public class ComparePurchaseByTitle implements Comparator<PurchaseHistory> {

		@Override
		public int compare(PurchaseHistory o1, PurchaseHistory o2) {
			String title1 = o1.getTitle();
			String title2 = o2.getTitle();
			return title1.compareTo(title2);
		}
	}
	
	/**
	 * Compare purchase history by descending title. 
	 * 
	 * @author Matthew Simpson
	 *
	 */
	public class ComparePurchaseByTitleDescending implements Comparator<PurchaseHistory> {

		@Override
		public int compare(PurchaseHistory o1, PurchaseHistory o2) {
			String title1 = o1.getTitle();
			String title2 = o2.getTitle();
			return title2.compareTo(title1);
		}
	}

}
