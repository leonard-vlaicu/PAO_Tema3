package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/tema3";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "root";
	private final Connection connection;

	// Constructor
	private DBConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = getConnection();
		} catch (ClassNotFoundException | SQLException exception) {
			throw new RuntimeException("Error connecting to database");
		}
	}


	private Connection getConnection	() throws SQLException{
		return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
	}
	public 	Connection getDBConnection	() {
		return this.connection;
	}

	private static final class 	DBConnectionHelper {
		private static final DBConnection INSTANCE = new DBConnection();
	}
	public  static 				DBConnection getInstance() {
		return DBConnectionHelper.INSTANCE;
	}
}
