/**
 * 
 */
package edu.buffalo.itembench.db;

/**
 * @author pketki
 * 
 */
public class JDBCConnector extends DatabaseConnector {

	public JDBCConnector(String name, String user, String password) {
		super(name, user, password);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getConnection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean query(Object query) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub

	}

}
