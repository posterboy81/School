/**
 * Project: COMP2613 - 09 - Lab 09
 * File: CustomerReader.java
 * Date: May 21, 2019
 * Time: 11:59:51 p.m.
 *
 * @author Matthew Simpson
 */

package lab09.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import lab09.data.Customer;
import lab09.data.validate.Validator;
import lab09.exceptions.ApplicationException;


public class CustomerReader {

	public static final String CUSTOMER_DELIMITER = ":";
	public static final String DATA_DELIMITER = "\\|";

	public static final int NUMBER_OF_ELEMENTS = 9;
	
	public static  String LOG4J_CONFIG_FILENAME = "log4j2.xml";
	private static Logger LOG;
	
	static {
		configureLogging();
		LOG = LogManager.getLogger();
	}


	/**
	 * Private constructor to prevent instantiation
	 */

	private CustomerReader() {
	}

	/**
	 * Method to take an incoming string and break it down into a customer object.
	 *
	 * @param fileInput,
	 *            the sting input generated from a file.
	 * @return newCustomer, a new Customer object.
	 * @throws ApplicationException
	 */
	public static Customer readFile(final String fileInput) throws ApplicationException {
		Customer newCustomer = null;
		if (!fileInput.isEmpty() || !fileInput.equals("")) {
			String[] inputData = fileInput.split(DATA_DELIMITER);
			if (inputData.length == NUMBER_OF_ELEMENTS) {
				String checkEmail = null;
				LOG.debug("Validating Email: " + inputData[7]);
				if (Validator.checkEmail(inputData[7]) == true) {
					checkEmail = inputData[7];
					LOG.debug(inputData[7] + " is is good!");

				} else {
					LOG.error(inputData[7] + " is not a valid email address");
					throw new ApplicationException(inputData[7] + " is not a valid email address");
				}

				newCustomer = new Customer.Builder(Integer.parseInt(inputData[0]), inputData[6]).firstName(inputData[1]).lastName(inputData[2])
						.streetName(inputData[3]).city(inputData[4]).postalCode(inputData[5]).emailAddress(checkEmail)
						.joinDate(LocalDate.parse(inputData[8], DateTimeFormatter.BASIC_ISO_DATE)).build();
			} else {
				String details = "[";

				for (String element : inputData) {
					details = details + element + ", ";
				}
				details = details + "]";
				LOG.error("Expected 9 elements but received " + inputData.length + ":" + details);
				throw new ApplicationException("Expected 9 elements but received " + inputData.length + ":" + details);
			}

		}

		LOG.debug("New Customer Created: " + newCustomer.toString());
		return newCustomer;
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
