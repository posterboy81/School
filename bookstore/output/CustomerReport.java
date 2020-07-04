/**
 * Project: Assignment 01
 * File: CustomerReport.java
 * Date: May 30, 2019
 * Time: 12:25:37 p.m.
 *
 * @author Matthew Simpson
 */


package ca.bcit.comp2613.bookstore.output;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import ca.bcit.comp2613.bookstore.data.Customer;

public class CustomerReport {

	private static final String LINE = "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
	private static final String FORMATTER = "%4d. %06d %-12s %-12s %-40s %-25s %-12s %-15s %-40s %-12s %7d%n";
	private static final String HEADER_FORMATTER = "%4s. %-6s %-12s %-12s %-40s %-25s %-12s %-15s %-40s %-12s %7s%n";
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy");

	public static String LOG4J_CONFIG_FILENAME = "log4j2.xml";
	private static Logger LOG;

	static {
		configureLogging();
		LOG = LogManager.getLogger();
	}

	/**
	 * Private constructor to prevent instantiation
	 */
	private CustomerReport() {
	}

	/**
	 * Method to print both print a report to the console and output a report to a
	 * file of all customers.
	 * 
	 * @param customerData - an ArrayList<Customer> - the pre-sorted data to be
	 *                     reported.
	 * @throws FileNotFoundException
	 */
	public static void outputCustomerReport(final ArrayList<Customer> customerData) throws FileNotFoundException {

		final Formatter output = new Formatter("customers_report.txt");
		LOG.info("OPENING FORMATTER" + output.toString());

		if (customerData != null) {
			try {

				output.format("Customer Report" + "%n");
				output.format(LINE + "%n");
				output.format(HEADER_FORMATTER, "#", "ID#", "First Name", "Last Name", "Street", "City",
						"Postal Code", "Phone #", "Email", "Join Date", "Length" + "%n");
				output.format(LINE + "%n");
				LOG.info("OUTPUTTING CUSTOMER REPORT TO FILE");
				for (int i = 0; i < customerData.size(); i++) {
					int cIndex = i;
					cIndex += 1;
					output.format(FORMATTER, cIndex, customerData.get(i).getId(),
							customerData.get(i).getFirstName(), customerData.get(i).getLastName(),
							customerData.get(i).getStreetName(), customerData.get(i).getCity(),
							customerData.get(i).getPostalCode(), customerData.get(i).getPhoneNumber(),
							customerData.get(i).getEmailAddress(),
							customerData.get(i).getJoinDate().format(DATE_FORMATTER), generateLength(customerData.get(i).getJoinDate()));
				}

				
				System.out.println("Customer Report");
				System.out.println(LINE);
				System.out.format(HEADER_FORMATTER, "#", "ID#", "First Name", "Last Name", "Street", "City",
						"Postal Code", "Phone #", "Email", "Join Date", "Length");
				System.out.println(LINE);
				LOG.info("OUTPUTTING CUSTOMER REPORT TO CONSOLE");

				for (int i = 0; i < customerData.size(); i++) {
					int cIndex = i;
					cIndex += 1;
					System.out.format(FORMATTER, cIndex, customerData.get(i).getId(),
							customerData.get(i).getFirstName(), customerData.get(i).getLastName(),
							customerData.get(i).getStreetName(), customerData.get(i).getCity(),
							customerData.get(i).getPostalCode(), customerData.get(i).getPhoneNumber(),
							customerData.get(i).getEmailAddress(),
							customerData.get(i).getJoinDate().format(DATE_FORMATTER), generateLength(customerData.get(i).getJoinDate()));
				}

			} finally {
				LOG.info("CLOSING FORMATTER");
				output.close();

			}
		}
	}

	/**
	 * Method to deterine the length of time in years since the customer joined.
	 * 
	 * @param date - the date to be checked. 
	 * @return years - the number of years. 
	 */
	private static int generateLength(LocalDate date) {
		Period timeFrame = Period.between(date, LocalDate.now());
		int years = timeFrame.getYears();
		return years;
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