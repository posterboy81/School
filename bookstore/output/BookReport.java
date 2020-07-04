/**
 * Project: Assignment 01
 * File: BookReport.java
 * Date: May 30, 2019
 * Time: 12:25:37 p.m.
 *
 * @author Matthew Simpson
 */

package ca.bcit.comp2613.bookstore.output;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import ca.bcit.comp2613.bookstore.data.Book;
import ca.bcit.comp2613.bookstore.validate.Validator;

public class BookReport {

	private static final String LINE = "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
	private static final String FORMATTER = "%08d %-12s %-40s %-40s %4d %6.3f %13d %-60s" + "%n";
	private static final String HEADER_FORMATTER = "%-8s %-12s %-40s %-40s %-4s %10s %13s %-60s" + "%n";

	public static String LOG4J_CONFIG_FILENAME = "log4j2.xml";
	private static Logger LOG;

	static {
		configureLogging();
		LOG = LogManager.getLogger();
	}

	/**
	 * Private Constructor
	 */
	private BookReport() {

	}

	/**
	 * Method to print the book report to the console and a file.
	 * 
	 * @param bookData - an ArrayList<Book>
	 * @throws FileNotFoundException
	 */
	public static void outputBookReport(final ArrayList<Book> bookData) throws FileNotFoundException {
		final Formatter output = new Formatter("book_report.txt");
		LOG.info("Opening Formatter" + output.toString());

		if (bookData != null) {
			try {
				output.format(LINE + "%n");
				output.format(HEADER_FORMATTER, "ID", "ISBN", "Authors", "Title", "Year", "Rating", "Ratings Count",
						"Image URL");
				output.format(LINE + "%n");
				LOG.info("OUTPUTTING BOOK REPORT TO FILE");
				for (int i = 0; i < bookData.size(); i++) {
					output.format(FORMATTER, bookData.get(i).getBookID(), bookData.get(i).getIsbn(),
							Validator.shortenString(bookData.get(i).getAuthors(), 40),
							Validator.shortenString(bookData.get(i).getOriginalTitle(), 40),
							bookData.get(i).getOriginalPubYear(), bookData.get(i).getAvgRating(),
							bookData.get(i).getRatingsCount(),
							Validator.shortenString(bookData.get(i).getImageURL(), 60));
				}

				System.out.format(LINE + "%n");
				System.out.format(HEADER_FORMATTER, "ID", "ISBN", "Authors", "Title", "Year", "Rating", "Ratings Count",
						"Image URL");
				System.out.format(LINE + "%n");
				LOG.info("OUTPUTTING BOOK REPORT TO CONSOLE");

				for (int i = 0; i < bookData.size(); i++) {
					System.out.format(FORMATTER, bookData.get(i).getBookID(), bookData.get(i).getIsbn(),
							Validator.shortenString(bookData.get(i).getAuthors(), 40),
							Validator.shortenString(bookData.get(i).getOriginalTitle(), 40),
							bookData.get(i).getOriginalPubYear(), bookData.get(i).getAvgRating(),
							bookData.get(i).getRatingsCount(),
							Validator.shortenString(bookData.get(i).getImageURL(), 60));
				}
			} finally {
				LOG.info("CLOSING FORMATTER");
				output.close();
			}
		}

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
