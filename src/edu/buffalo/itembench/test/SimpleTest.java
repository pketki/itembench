/**
 * 
 */
package edu.buffalo.itembench.test;

import java.sql.Connection;

import edu.buffalo.itembench.db.DbConnector;
import edu.buffalo.itembench.db.DbException;
import edu.buffalo.itembench.workloads.Workload;

/**
 * @author pketki
 * 
 */
public class SimpleTest {

	private DbConnector dbConn;
	private MetricsBean metrics;

	public SimpleTest(DbConnector dbConn) {
		this.dbConn = dbConn;
		metrics = new MetricsBean();
	}

	public void run(Workload workload) throws DbException {
		Connection connection = dbConn.getConnection();
		workload.init(connection);
		dbConn.closeConnection();

		// TODO: start measuring time
		connection = dbConn.getConnection();
		workload.run(connection);
		dbConn.closeConnection();
		// TODO: stop measuring time
	}

}
