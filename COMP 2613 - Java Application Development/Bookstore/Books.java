/**
 * Project: Assignment 01
 * File: Books.java
 * Date: May 30, 2019
 * Time: 12:25:37 p.m.
 *
 * @author Matthew Simpson
 */

package ca.bcit.comp2613.bookstore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.cli.ParseException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.DefaultConfiguration;

import ca.bcit.comp2613.bookstore.data.Book;
import ca.bcit.comp2613.bookstore.data.Customer;
import ca.bcit.comp2613.bookstore.data.Purchase;
import ca.bcit.comp2613.bookstore.data.Purchase.PurchaseHistory;
import ca.bcit.comp2613.bookstore.exception.ApplicationException;
import ca.bcit.comp2613.bookstore.input.BookReader;
import ca.bcit.comp2613.bookstore.input.CustomerReader;
import ca.bcit.comp2613.bookstore.input.PurchaseReader;
import ca.bcit.comp2613.bookstore.output.BookReport;
import ca.bcit.comp2613.bookstore.output.CustomerReport;
import ca.bcit.comp2613.bookstore.output.PurchaseReport;
import ca.bcit.comp2613.bookstore.sort.Comparators;

/**
 * Project: Book File: BookStore.java Date: October, 2017 Time: 1:22:25 PM
 */

public class Books {

	private static final String LOG4J_CONFIG_FILENAME = "log4j2.xml";
	static {
		configureLogging();
	}
	private static final Logger LOG = LogManager.getLogger();

	private Map<Integer, Customer> customerDB;
	private Map<Integer, Book> booksDB;
	private Map<Integer, Purchase> purchasesDB;

	/**
	 * Constructor
	 * 
	 * @throws ApplicationException
	 * @throws ParseException
	 */
	public Books(String[] args) throws ApplicationException, ParseException {
		customerDB = new HashMap<Integer, Customer>();
		booksDB = new HashMap<Integer, Book>();
		purchasesDB = new HashMap<Integer, Purchase>();

		BookOptions.process(args);
	}

	/**
	 * Entry point to GIS
	 * 
	 * @param args
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) {
		Instant startTime = Instant.now();
		LOG.info("------------------------------- NEW RUN -------------------------------");
		LOG.info(startTime);

		LOG.info("Start the book system");
		try {
			Books book = new Books(args);
			if (BookOptions.isHelpOptionSet()) {
				BookOptions.Value[] values = BookOptions.Value.values();
				System.out.format("%-5s %-15s %-10s %s%n", "Option", "Long Option", "Has Value", "Description");
				for (BookOptions.Value value : values) {
					System.out.format("-%-5s %-15s %-10s %s%n", value.getOption(), ("-" + value.getLongOption()),
							value.isHasArg(), value.getDescription());
				}

				return;
			}

			LOG.debug("run()");
			book.run();
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}

		Instant endTime = Instant.now();
		LOG.info(endTime);
		LOG.info(String.format("Duration: %d ms", Duration.between(startTime, endTime).toMillis()));
	}

	/**
	 * Configures log4j2 from the external configuration file specified in
	 * LOG4J_CONFIG_FILENAME. If the configuration file isn't found then log4j2's
	 * DefaultConfiguration is used.
	 */
	private static void configureLogging() {
		ConfigurationSource source;
		try {
			source = new ConfigurationSource(new FileInputStream(LOG4J_CONFIG_FILENAME));
			Configurator.initialize(null, source);
		} catch (IOException e) {
			System.out.println(String.format(
					"WARNING! Can't find the log4j logging configuration file %s; using DefaultConfiguration for logging.",
					LOG4J_CONFIG_FILENAME));
			Configurator.initialize(new DefaultConfiguration());
		}
	}

	/**
	 * Run method runs the program.
	 * 
	 * @throws ApplicationException
	 * @throws IOException
	 * 
	 */
	private void run() throws ApplicationException, IOException {
		LOG.debug("readCustomers()");
		readCustomers();

		LOG.debug("readBooks()");
		readBooks();

		LOG.debug("generatePurchases()");
		readPurchaseList();

		LOG.debug("generateReports()");
		generateReports();
	}

	/**
	 * Method to read the customer data from a file using scanner and add it to a
	 * HashMap<Integer, Customer>
	 * 
	 * @throws ApplicationException
	 */
	private void readCustomers() throws ApplicationException {
		LOG.info("STARTING SCANNER");
		Scanner customerInput = null;
		try {
			customerInput = new Scanner(new File("customers.dat"));

		} catch (FileNotFoundException e) {
			LOG.error("ERROR: " + e.getMessage());
			System.exit(-1);
		}
		customerInput.nextLine();

		try {
			while (customerInput.hasNext()) {
				String customerDetails = customerInput.nextLine();
				Customer newCustomer = CustomerReader.readData(customerDetails);
				customerDB.put(newCustomer.getId(), newCustomer);
				LOG.info("CUSTOMER ADDED TO MAP: " + customerDB.get(newCustomer.getId()).getLastName());
			}
		} catch (DateTimeParseException | NullPointerException e) {
			LOG.error("ERROR: " + e.getMessage());
		} finally {
			LOG.info("CLOSING SCANNER");
			customerInput.close();
		}

	}

	/**
	 * Method to read the books data from a file using Reader and add it to a
	 * HashMap<Integer, Book>.
	 * 
	 * @throws ApplicationException
	 * @throws IOException
	 */
	private void readBooks() throws ApplicationException, IOException {
		LOG.info("STARTING CSV READER");
		Reader bookSourceFile = new FileReader("books500.csv");
		Iterable<CSVRecord> bookRecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(bookSourceFile);
		try {
			for (CSVRecord index : bookRecords) {
				Book newBook = BookReader.readCSV(index);
				booksDB.put(newBook.getBookID(), newBook);
				LOG.info("BOOK ADDED TO MAP: " + booksDB.get(newBook.getBookID()).getOriginalTitle());
			}
		} catch (ApplicationException e) {
			LOG.error("ERROR: " + e.getMessage());
		} finally {
			LOG.info("CLOSING CSV READER");
			bookSourceFile.close();
		}

	}

	/**
	 * Method to read the purchase history data from a file and add it to a
	 * HashMap<Integer, Purchase>.
	 * 
	 * @throws IOException
	 * @throws ApplicationException
	 */
	private void readPurchaseList() throws IOException, ApplicationException {
		LOG.info("STARTING CSV READER");
		Reader purchaseSourceFile = new FileReader("purchases.csv");
		Iterable<CSVRecord> purchaseRecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(purchaseSourceFile);

		try {
			for (CSVRecord index : purchaseRecords) {
				Purchase newPurchase = PurchaseReader.readPurchases(index);
				purchasesDB.put(newPurchase.getPurchaseID(), newPurchase);
				LOG.info("PURCHASE ADDED TO MAP: " + purchasesDB.get(newPurchase.getPurchaseID()).getBookID());

			}
		} finally {
			LOG.info("CLOSING CSV READER");
			purchaseSourceFile.close();
		}
	}

	/**
	 * Generate the reports from the input data
	 * 
	 * @throws FileNotFoundException
	 */
	private void generateReports() throws FileNotFoundException {
		LOG.debug("GENERATING REPORTS");
		Comparators c = new Comparators();

		ArrayList<Customer> sortedCustomers = new ArrayList<Customer>(customerDB.values());
		if (BookOptions.isCustomersOptionSet() == true) {
			LOG.debug("GENERATING THE CUSTOMER REPORT");

			if (BookOptions.isByJoinDateOptionSet() == true) {
				LOG.info("SORT BY JOIN DATE");
				sortedCustomers.sort(c.new CompareByJoinDate());

				if (BookOptions.isDescendingOptionSet() == true) {
					LOG.info("SORT DESCENDING");
					sortedCustomers.sort(c.new CompareByJoinDateDescending());
				}

			}

			CustomerReport.outputCustomerReport(sortedCustomers);
		}

		ArrayList<Book> sortedBooks = new ArrayList<Book>(booksDB.values());
		if (BookOptions.isBooksOptionSet() == true) {
			LOG.debug("GENERATING THE BOOK REPORT");

			if (BookOptions.isByAuthorOptionSet() == true) {
				LOG.info("SORT BY AUTHOR");
				sortedBooks.sort(c.new CompareByAuthorName());

				if (BookOptions.isDescendingOptionSet() == true) {
					LOG.info("SORT DESCENDING");
					sortedBooks.sort(c.new CompareByAuthorNameDescending());

				}
			}

			BookReport.outputBookReport(sortedBooks);

		}

		if (BookOptions.isPurchasesOptionSet() == true) {
			LOG.debug("GENERATING THE PURCHASES REPORT");
			Purchase p = new Purchase();
			ArrayList<Purchase> purchaseData = new ArrayList<Purchase>(purchasesDB.values());
			ArrayList<PurchaseHistory> history = new ArrayList<PurchaseHistory>();

			for (Purchase pIndex : purchaseData) {
				String firstName = customerDB.get(pIndex.getCustomerID()).getFirstName();
				String lastName = customerDB.get(pIndex.getCustomerID()).getLastName();
				String title = booksDB.get(pIndex.getBookID()).getOriginalTitle();
				float price = pIndex.getPrice();
				PurchaseHistory newEntry = p.new PurchaseHistory(firstName, lastName, title, price);
				history.add(newEntry);
			}

			if (BookOptions.getCustomerId() != null) {
				LOG.debug("GENERATING REPORT BY CUSTOMER ID");
				ArrayList<PurchaseHistory> temp = new ArrayList<PurchaseHistory>();
				for (Purchase pIndex : purchaseData) {

					if (pIndex.getCustomerID() == Integer.parseInt(BookOptions.getCustomerId())) {
						String firstName = customerDB.get(pIndex.getCustomerID()).getFirstName();
						String lastName = customerDB.get(pIndex.getCustomerID()).getLastName();
						String title = booksDB.get(pIndex.getBookID()).getOriginalTitle();
						float price = pIndex.getPrice();
						PurchaseHistory newEntry = p.new PurchaseHistory(firstName, lastName, title, price);
						temp.add(newEntry);

					}
				}
				history = temp;

			}

			if (BookOptions.isByLastnameOptionSet() == true) {
				LOG.info("SORT BY LAST NAME");
				history.sort(c.new ComparePurchaseByLastName());

				if (BookOptions.isDescendingOptionSet() == true) {
					LOG.info("SORT DESCENDING");
					history.sort(c.new ComparePurchaseByLastNameDescending());
				}

			}

			if (BookOptions.isByTitleOptionSet() == true) {
				LOG.info("SORT BY LAST TITLE");
				history.sort(c.new ComparePurchaseByTitle());

				if (BookOptions.isDescendingOptionSet() == true) {
					LOG.info("SORT DESCENDING");
					history.sort(c.new ComparePurchaseByTitleDescending());
				}
			}

			PurchaseReport.outputPurchaseReport(history, BookOptions.isTotalOptionSet());

		}
	}

}
