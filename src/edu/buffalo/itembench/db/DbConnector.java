/**
 * Database connector to connect and query a DB using JDBC
 */
package edu.buffalo.itembench.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author pketki
 * 
 */
public class DbConnector {

	private String driver;
	private String connectionURL;
	private String user;
	private String password;
	private String dbName;
	private Connection connection;

	public DbConnector() throws DbException {
		super();
		Properties props = new Properties();
		try {
			props.load(getClass().getResourceAsStream("/database.properties"));
			driver = props.getProperty("driver");
			connectionURL = props.getProperty("url");
			// user = props.getProperty("user");
			// password = props.getProperty("password");
			dbName = props.getProperty("dbName");
		} catch (IOException e) {
			throw new DbException("Database Properties missing", e);
		}
	}

	public Connection getConnection() throws DbException {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(connectionURL + dbName,
					user, password);
		} catch (ClassNotFoundException e) {
			throw new DbException("Incorrect Database Driver", e);
		} catch (SQLException e) {
			throw new DbException("Unable to connect to DB", e);
		}

		return connection;
	}

	public ResultSet query(String query) throws DbException {
		ResultSet resultSet = null;
		try {
			// TODO: Use statement instead?
			PreparedStatement statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
		} catch (SQLException e) {
			throw new DbException("Invalid query or unable to connect to DB", e);
		}
		return resultSet;
	}

	public void closeConnection() throws DbException {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new DbException("Unable to close connection", e);
		}
		connection = null;
	}

}
