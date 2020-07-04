/**
 * Project: COMP2613 - 09 - Lab 09
 * File: Lab09.java
 * Date: June 16, 2019
 *
 * @author Matthew Simpson
 */

package lab09;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import lab09.data.Customer;
import lab09.database.CustomerDAO;
import lab09.database.DAOTester;
import lab09.database.DBUtil;
import lab09.exceptions.ApplicationException;
import lab09.io.CustomerReader;
import lab09.ui.MainFrame;


public class Lab09 {

	static Instant startTime, endTime;

	private Properties dbProperties;
	private FileInputStream propSourceFile;
	private static Connection dbConnection;

	private static CustomerDAO dao;
	private DAOTester dt;

	public static final String TABLE_NAME = "a00820997_Customer";

	public static String LOG4J_CONFIG_FILENAME = "log4j2.xml";
	private static Logger LOG;

	static {
		configureLogging();
		LOG = LogManager.getLogger();
	}

	/**
	 * The Lab05 constructor. Sets up the HashMap.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * 
	 */
	public Lab09(String[] args) throws IOException, ClassNotFoundException, SQLException {
		new HashMap<Long, Customer>();

		dbProperties = new Properties();
		propSourceFile = new FileInputStream("db.properties");
		dbProperties.load(propSourceFile);
		establishConnection();
		dao = new CustomerDAO(dbConnection);
		dt = new DAOTester(dbConnection, dao);

	}

	/**
	 * The main method.
	 *
	 * @param args
	 * @throws ApplicationException
	 */
	public static void main(String[] args) throws ApplicationException {

		startTime = Instant.now();

		LOG.info("Database Read Start: " + startTime);
		System.out.println();

		try {
			new Lab09(args).run();
		} catch (IOException e) {
			LOG.error(e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		endTime = Instant.now();
		LOG.info("Database Read End: " + endTime);
		long nanoseconds = Duration.between(startTime, endTime).toMillis();
		LOG.info("Duration: " + nanoseconds + " milliseconds");

	}

	/**
	 * run the CustomerReader to break the data down into Customer objects and then
	 * run CustomerReport to print out the formatted report.
	 *
	 * @throws ApplicationException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void run() throws ApplicationException, ClassNotFoundException, SQLException {

		Class.forName(dbProperties.getProperty("db.driver"));

		if (DBUtil.tableExists(dbConnection, TABLE_NAME) == false) {
			try {
				LOG.debug("TABLE DOES NOT EXIST - CREATING NEW");
				dao.create();
			} catch (SQLException e) {
				LOG.error(e.getMessage());
			}
		} else {
			LOG.info("DROP OPTION ENABLED, DROPPING TABLE");
			dao.drop();
			LOG.info("CREATING NEW DATABASE");
			dao.create();

		}

		Scanner input = null;
		LOG.info("Starting Scanner");
		try {
			input = new Scanner(new File("customers.txt"));
		} catch (FileNotFoundException e) {
			LOG.error("ERROR: " + e.getMessage());
			System.exit(-1);
		}

		// skip ahead one line past the header data.
		input.nextLine();

		try {
			while (input.hasNext()) {
				String inputNewRow = input.nextLine();
				LOG.debug("Input Data: " + inputNewRow);
				Customer tempCustomer = CustomerReader.readFile(inputNewRow);
				System.out.println(tempCustomer.getFirstName());
				dao.add(tempCustomer);
				System.out.println();
			}
		} catch (DateTimeParseException | NullPointerException e) {
			LOG.error("ERROR: " + e.getMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			LOG.debug("Closing Scanner");
			input.close();
		}

		try {
			dt.databaseTest();
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame(dbConnection, dao);
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Establish the Database Connection
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private void establishConnection() throws SQLException, ClassNotFoundException {
		dbConnection = DriverManager.getConnection(dbProperties.getProperty("db.url"),
				dbProperties.getProperty("db.user"), dbProperties.getProperty("db.password"));
		LOG.info("DATABASE CONNECTION ESTABLISHED");
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
