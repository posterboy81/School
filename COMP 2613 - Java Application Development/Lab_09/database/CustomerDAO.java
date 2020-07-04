package lab09.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import lab09.Lab09;
import lab09.data.Customer;

public class CustomerDAO implements DAO {

	Connection connection;

	public static String LOG4J_CONFIG_FILENAME = "log4j2.xml";
	private static Logger LOG;

	static {
		configureLogging();
		LOG = LogManager.getLogger();

	}

	public CustomerDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create() throws SQLException {
		if (connection != null) {
			String createString = String.format("CREATE TABLE " + Lab09.TABLE_NAME
					+ " (id VARCHAR(4), firstName VARCHAR(11), lastName VARCHAR(11), street VARCHAR(25), city VARCHAR(12), postalCode VARCHAR(12), phoneNumber VARCHAR(15) , email VARCHAR(25), joinDate DATE, PRIMARY KEY (id))");
			Statement statement = connection.createStatement();
			statement.executeUpdate(createString);
		}
	}

	@Override
	public void add(Customer addCustomer) throws SQLException {
		if (connection != null) {
			List<String> ids = getIds();

			if (ids.contains(String.valueOf(addCustomer.getId()))) {
				LOG.debug("Customer ID " + addCustomer.getId() + " already exists in database - skipping");
			} else {

				String createString = String.format(
						"INSERT INTO %s values('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')", Lab09.TABLE_NAME,
						addCustomer.getId(), addCustomer.getFirstName(), addCustomer.getLastName(),
						addCustomer.getStreetName(), addCustomer.getCity(), addCustomer.getPostalCode(),
						addCustomer.getPhoneNumber(), addCustomer.getEmailAddress(), addCustomer.getJoinDate());
				System.out.println(createString);
				Statement statement = connection.createStatement();
				statement.executeUpdate(createString);
				LOG.debug("CUSTOMER ADDED TO DATABASE: " + createString);
			}
		}
	}

	@Override
	public void deleteCustomer(int id) throws SQLException {
		if (connection != null) {
			String deleteString = "DELETE FROM " + Lab09.TABLE_NAME + " WHERE id = '" + id + "'";
			Statement s = connection.createStatement();
			s.executeUpdate(deleteString);
		}
	}

	@Override
	public void readAll() throws SQLException {
		if (connection != null) {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM " + Lab09.TABLE_NAME);
			ArrayList<Customer> custList = new ArrayList<Customer>();
			System.out.println();

			Customer tempCustomer;
			while (rs.next()) {
				tempCustomer = new Customer.Builder(rs.getInt("id"), rs.getString("phoneNumber"))
						.firstName(rs.getString("firstName")).lastName(rs.getString("lastName"))
						.streetName(rs.getString("street")).city(rs.getString("city"))
						.postalCode(rs.getString("postalCode")).emailAddress(rs.getString("email"))
						.joinDate(LocalDate.parse(rs.getString("joinDate"))).build();

				custList.add(tempCustomer);
			}
			System.out.println(custList.toString());
		}
	}

	@Override
	public Customer getCustomer(int id) throws SQLException {
		Customer tempCustomer = null;
		if (connection != null) {
			String query = "SELECT * FROM " + Lab09.TABLE_NAME + " WHERE id='" + id + "'";
			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(query);
			System.out.println(id);
			System.out.println(query);
			while (rs.next()) {
				tempCustomer = new Customer.Builder(rs.getInt("id"), rs.getString("phoneNumber"))
						.firstName(rs.getString("firstName")).lastName(rs.getString("lastName"))
						.streetName(rs.getString("street")).city(rs.getString("city"))
						.postalCode(rs.getString("postalCode")).emailAddress(rs.getString("email"))
						.joinDate(LocalDate.parse(rs.getString("joinDate"))).build();
			}
		}
		return tempCustomer;

	}

	@Override
	public void updateCustomer(int id, String field, String newValue) throws SQLException {
		if (connection != null) {
			Statement s = connection.createStatement();
			String update = "UPDATE " + Lab09.TABLE_NAME + " SET '" + field + "' = '" + newValue + "' WHERE id = '" + id
					+ "'";
			s.executeUpdate(update);
		}
	}

	@Override
	public void drop() throws SQLException {
		Statement statement = connection.createStatement();
		String dropString = "DROP TABLE " + Lab09.TABLE_NAME;
		statement.executeUpdate(dropString);
	}

	@Override
	public void close() throws SQLException {
		if (connection != null) {
			connection.close();
			LOG.info("CLOSING CONNECTION");
		}
	}

	@Override
	public List<String> getIds() throws SQLException {
		List<String> ids = new ArrayList<String>();
		if (connection != null) {
			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery("SELECT id FROM " + Lab09.TABLE_NAME);

			while (rs.next()) {
				ids.add(rs.getString("id"));
			}

			// System.out.println(ids);
		}

		return ids;
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
