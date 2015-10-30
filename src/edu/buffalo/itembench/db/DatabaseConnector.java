/**
 * 
 */
package edu.buffalo.itembench.db;

/**
 * @author jlimaye
 * 
 */
public class DatabaseConnector {

	private String name;
	private String user;
	private String password;

	/**
	 * @param name
	 * @param user
	 * @param password
	 */
	public DatabaseConnector(String name, String user, String password) {
		super();
		this.name = name;
		this.user = user;
		this.password = password;
	}

	public Object getConnection(){return null;}

	public boolean query(Object query){return false;}

	public void closeConnection(){}

}
