package lab09.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import lab09.data.Customer;

public class DAOTester {

	private Connection c;
	private CustomerDAO dao;

	public static String LOG4J_CONFIG_FILENAME = "log4j2.xml";
	private static Logger LOG;

	static {
		configureLogging();
		LOG = LogManager.getLogger();

	}

	public DAOTester(Connection c, CustomerDAO dao) {
		this.c = c;
		this.dao = dao;
	}

	public void databaseTest() throws SQLException {
		if (c != null) {
			System.out.println();
			LOG.debug("COMMENCING DAO TEST");
			List<String> ids = dao.getIds();
			Customer temp = null;

			System.out.println("Loaded " + ids.size() + " IDs from the database.");
			System.out.println(ids.toString());
			System.out.println();

			for (int i = 0; i < ids.size(); i++) {
				temp = dao.getCustomer(Integer.parseInt(ids.get(i)));
				System.out.println(temp.toString());
				System.out.println();
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
