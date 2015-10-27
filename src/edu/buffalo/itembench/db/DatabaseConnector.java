/**
 * 
 */
package edu.buffalo.itembench.db;

/**
 * @author jlimaye
 * 
 */
public abstract class DatabaseConnector {

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

	public abstract Object getConnection();

	public abstract boolean query(Object query);

	public abstract void closeConnection();

}
