/**
 * 
 */
package edu.buffalo.itembench.test;

import java.io.IOException;
import java.sql.Connection;
import java.util.Date;

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

		// TODO: start measuring metrics
		Long start = new Date().getTime();
		connection = dbConn.getConnection();
		try {
			workload.run(connection);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConnection();
			System.out.println(new Date().getTime() - start);
		}
		// TODO: stop measuring metrics
	}

}
