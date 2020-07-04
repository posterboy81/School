/**
 * Project: Assignment 01
 * File: PurchaseReport.java
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

import ca.bcit.comp2613.bookstore.data.Purchase.PurchaseHistory;
import ca.bcit.comp2613.bookstore.validate.Validator;

public class PurchaseReport {

	private static final String LINE = "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
	public static final String FORMATTER = "%-24s %-80s $%.2f" + "%n";
	public static final String HEADER_FORMATTER = "%-24s %-80s %2s" + "%n";

	public static String LOG4J_CONFIG_FILENAME = "log4j2.xml";
	private static Logger LOG;

	static {
		configureLogging();
		LOG = LogManager.getLogger();
	}

	/**
	 * Private constructor
	 */
	private PurchaseReport() {

	}

	/**
	 * Method to print purchase history to the console and a file.
	 * 
	 * @param purchaseHistory - an ArrayList<PurchaseHistory>
	 * @param tot             - a Boolean, whether the total cost of purchases is to
	 *                        be included.
	 * @throws FileNotFoundException
	 */
	public static void outputPurchaseReport(final ArrayList<PurchaseHistory> purchaseHistory, boolean tot)
			throws FileNotFoundException {

		final Formatter output = new Formatter("purchases_report.txt");
		LOG.info("OPENING FORMATTER" + output.toString());

		try {
			float totalCost = 0;
			output.format("Customer report" + "%n");
			output.format(LINE);
			output.format(HEADER_FORMATTER, "Name", "Title", "Price");
			LOG.info("OUTPUTTING PURCHASE REPORT TO FILE");

			for (PurchaseHistory phIndex : purchaseHistory) {
				output.format(FORMATTER, phIndex.getFirstName() + " " + phIndex.getLastName(),
						Validator.shortenString(phIndex.getTitle(), 80), phIndex.getPrice());
				totalCost += phIndex.getPrice();
			}

			System.out.println("Customer Report");
			System.out.println(LINE);
			System.out.format(HEADER_FORMATTER, "Name", "Title", "Price");
			System.out.println(LINE);
			LOG.info("OUTPUTTING PURCHASE REPORT TO CONSOLE");

			for (PurchaseHistory phIndex : purchaseHistory) {
				System.out.format(FORMATTER, phIndex.getFirstName() + " " + phIndex.getLastName(),
						Validator.shortenString(phIndex.getTitle(), 80), phIndex.getPrice());
			}

			if (tot == true) {
				System.out.println();
				System.out.println("Value of Purchases: $" + String.format("%.2f", totalCost));
				System.out.println();
				output.format("%n");
				output.format("Value of Purchases: $" + String.format("%.2f", totalCost));
				output.format("%n");
			}

		} finally {
			LOG.info("CLOSING FORMATTER");
			output.close();
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
