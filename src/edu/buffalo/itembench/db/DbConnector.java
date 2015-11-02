/**
 * 
 */
package edu.buffalo.itembench.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
        private final String RESOURCE="resources/database.properties";
        

	public DbConnector(String driver,String url,String dbName) {
		super();
                Properties props=new Properties();
                
                InputStream is = null;
 
                // First try loading from the current directory
                try {
                    File f = new File(RESOURCE);
                    is = new FileInputStream( f );
                    props.load( is );
                }
                catch ( Exception e ) { 
                    is = null; 
                    System.out.println("Exception "+e);
                }
                
                //Get properties from file
		this.driver = props.getProperty(driver);
		this.connectionURL = props.getProperty(url);
		this.dbName = props.getProperty(dbName);
                this.user = props.getProperty("user");
		this.password = props.getProperty("password");
		
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
                
                System.out.println("Opened database successfully");
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
                System.out.println("Connection closed!");
		connection = null;
	}

}
