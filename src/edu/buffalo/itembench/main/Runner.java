/**
 * 
 */
package edu.buffalo.itembench.main;

import edu.buffalo.itembench.db.DbConnector;
import edu.buffalo.itembench.db.DbException;
import edu.buffalo.itembench.test.MetricsBean;
import edu.buffalo.itembench.test.SimpleTest;
import edu.buffalo.itembench.workloads.Workload;
import edu.buffalo.itembench.workloads.rfid.AuthenticationWorkload;
import edu.buffalo.itembench.workloads.rfid.WriteOnlyWorkload;

/**
 * @author pketki
 * 
 */
public class Runner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DbConnector dbConn;
		try {
			dbConn = new DbConnector();
			SimpleTest test = new SimpleTest(dbConn);
			Workload workload = new WriteOnlyWorkload();
			 for (int i = 0; i < 3; i++) {
				test.run(workload);
				MetricsBean metrics = test.getMetrics();
				System.out.println(metrics.toString());
			 }
			//TODO: write metrics to file

		} catch (DbException e) {
			e.printStackTrace();
		}
	}

}
