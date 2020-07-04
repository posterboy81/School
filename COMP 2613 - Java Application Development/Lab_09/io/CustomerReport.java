/**
 * Project: COMP2613 - 09 - Lab 09
 * File: CustomerReport.java
 * Date: May 21, 2019
 * Time: 11:59:51 p.m.
 *
 * @author Matthew Simpson
 */

package lab09.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import lab09.data.Customer;

public class CustomerReport {

	private static final String LINE = "------------------------------------------------------------------------------------------------------------------------------------------";
	private static final String FORMATTER = "%-4s %-11s %-11s %-25s %-12s %-12s %-15s %-25s %-10s%n";
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy");
	
	public static  String LOG4J_CONFIG_FILENAME = "log4j2.xml";
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
	 * Method to print both print a report to the console and output a report to a file of all customers, sorted by join date.
	 * 
	 * @param customerData
	 *            - an ArrayList<Customer> - the pre-sorted data to be reported.
	 * @throws FileNotFoundException
	 */
	public static void outputReport(final ArrayList<Customer> customerData) throws FileNotFoundException {
		
		final Formatter output = new Formatter("customers.report.txt");
		LOG.info("Opening Formatter" + output.toString());

		if (customerData != null) {
			try {
				LOG.info("Outputting report file header");
				output.format("Customer Report" + "%n");
				output.format(LINE + "%n");
				output.format(FORMATTER, "ID#", "First Name", "Last Name", "Street", "City", "Postal Code", "Phone #", "Email", "Join Date" + "%n");
				output.format(LINE + "%n");
				
				LOG.info("Outputting report file contents:");
				for (Customer index : customerData) {
					output.format(FORMATTER, index.getId(), index.getFirstName(), index.getLastName(), index.getStreetName(), index.getCity(),
							index.getPostalCode(), index.getPhoneNumber(), index.getEmailAddress(), index.getJoinDate().format(DATE_FORMATTER));
					LOG.debug("Customer: " + index.getId() + ", " + index.getFirstName() + " " + index.getLastName());
				}
				System.out.println();

				System.out.println("Customer Report");
				System.out.println(LINE);
				System.out.format(FORMATTER, "ID#", "First Name", "Last Name", "Street", "City", "Postal Code", "Phone #", "Email", "Join Date");
				System.out.println(LINE);
				LOG.info("Outputting report to console");
				for (int i = 0; i < customerData.size(); i++) {
					System.out.format(FORMATTER, customerData.get(i).getId(), customerData.get(i).getFirstName(), customerData.get(i).getLastName(), customerData.get(i).getStreetName(), customerData.get(i).getCity(),
							customerData.get(i).getPostalCode(), customerData.get(i).getPhoneNumber(), customerData.get(i).getEmailAddress(), customerData.get(i).getJoinDate().format(DATE_FORMATTER));
				}

			} finally {
				LOG.info("Closing formatter");
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
            System.out.println(String.format(
              "Can't find the log4j logging configuration file %s.", LOG4J_CONFIG_FILENAME));
        }
    }
}
