/**
 * Project: Assignment 01
 * File: BookReader.java
 * Date: May 30, 2019
 * Time: 12:25:37 p.m.
 *
 * @author Matthew Simpson
 */

package ca.bcit.comp2613.bookstore.input;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import ca.bcit.comp2613.bookstore.data.Book;
import ca.bcit.comp2613.bookstore.exception.ApplicationException;

public class BookReader {

	public static final int NUMBER_OF_ELEMENTS = 8;
	public static String LOG4J_CONFIG_FILENAME = "log4j2.xml";
	private static Logger LOG;

	static {
		configureLogging();
		LOG = LogManager.getLogger();
	}

	/**
	 * Private Constructor
	 */
	private BookReader() {
	}

	/**
	 * Method to read book data from a CSV file.
	 * 
	 * @param bookInput - the CSV file data
	 * @return newBook - a new Book object.
	 * @throws ApplicationException
	 */
	public static Book readCSV(CSVRecord bookInput) throws ApplicationException {
		Book newBook = null;
		if (bookInput != null) {
			if (bookInput.size() == NUMBER_OF_ELEMENTS) {

				newBook = new Book.Builder(Integer.parseInt(bookInput.get(0)), bookInput.get(4)).isbn(bookInput.get(1))
						.authors(bookInput.get(2)).originalPubYear(Integer.parseInt(bookInput.get(3)))
						.avgRating(Float.parseFloat(bookInput.get(5))).ratingsCount(Integer.parseInt(bookInput.get(6)))
						.imageURL(bookInput.get(7)).build();

			} else {
				String details = "[";
				for (Iterator<String> iterator = bookInput.iterator(); iterator.hasNext();) {
					String index = iterator.next();
					details = details + index;
				}
				details = details + "]";
				LOG.error("ERROR: Expected 8 elements but received " + bookInput.size() + ":" + details);
				throw new ApplicationException(
						"ERROR: Expected 8 elements but received " + bookInput.size() + ":" + details);
			}

		}
		LOG.debug("NEW BOOK CREATED: " + newBook.toString());
		return newBook;
	}

	/**
	 * Logging
	 */
	private static void configureLogging() {
		ConfigurationSource source;
		try {
			source = new ConfigurationSource(new FileInputStream(LOG4J_CONFIG_FILENAME));
			Configurator.initialize(null, source);
		} catch (IOException e) {
			System.out.println(
					String.format("Can't find the log4j logging configuration file %s.", LOG4J_CONFIG_FILENAME));
		}
	}

}