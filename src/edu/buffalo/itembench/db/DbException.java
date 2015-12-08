/**
 * Exceptions thrown by DbConnector including connection as well as query exceptions
 */
package edu.buffalo.itembench.db;

/**
 * @author pketki
 *
 */
public class DbException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5888022122283090375L;

	/**
	 * 
	 */
	public DbException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public DbException(String arg0, Throwable arg1) {
		System.out.println(arg0);
		arg1.printStackTrace();
	}

}
