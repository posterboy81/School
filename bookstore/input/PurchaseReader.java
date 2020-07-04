/**
 * Project: Assignment 01
 * File: PurchaseReader.java
 * Date: May 30, 2019
 * Time: 12:25:37 p.m.
 *
 * @author Matthew Simpson
 */

package ca.bcit.comp2613.bookstore.input;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import ca.bcit.comp2613.bookstore.data.Purchase;

public class PurchaseReader {

	public static final int NUMBER_OF_ELEMENTS = 4;

	public static String LOG4J_CONFIG_FILENAME = "log4j2.xml";
	private static Logger LOG;

	static {
		configureLogging();
		LOG = LogManager.getLogger();
	}

	/**
	 * Private Constructor
	 */
	private PurchaseReader() {

	}

	/**
	 * Method to read purchase data from a CSV file and break it down into purchase
	 * objects.
	 * 
	 * @param purchaseData - the CSV data
	 * @return newPurchase - the new purchase object.
	 */
	public static Purchase readPurchases(CSVRecord purchaseData) {
		Purchase newPurchase = null;
		if (purchaseData != null) {
			if (purchaseData.size() == NUMBER_OF_ELEMENTS) {

				newPurchase = new Purchase.Builder(Integer.parseInt(purchaseData.get(0)),
						Integer.parseInt(purchaseData.get(1)), Integer.parseInt(purchaseData.get(2)))
								.price(Float.parseFloat(purchaseData.get(3))).build();

			} else {

			}
		}
		LOG.debug("NEW PURCHASE RECORD: " + newPurchase.toString());
		return newPurchase;
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
