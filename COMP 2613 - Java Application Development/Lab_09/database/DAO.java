package lab09.database;

import java.sql.SQLException;
import java.util.List;

import lab09.data.Customer;


public interface DAO {
	
	public abstract void create() throws SQLException;

	public abstract void add(Customer addCustomer) throws SQLException;
		
	public abstract void readAll() throws SQLException;

	public abstract Customer getCustomer(int id) throws SQLException;
	
	public abstract void deleteCustomer(int id) throws SQLException;
	
	public abstract void drop() throws SQLException;

	public abstract void close() throws SQLException;

	public abstract void updateCustomer(int id, String field, String newValue) throws SQLException;
	
	public abstract List<String> getIds() throws SQLException;


}
