/**
 * 
 */
package edu.buffalo.itembench.main;

import edu.buffalo.itembench.db.DbConnector;
import edu.buffalo.itembench.db.DbException;
import edu.buffalo.itembench.test.MetricsBean;
import edu.buffalo.itembench.test.SimpleTest;
import edu.buffalo.itembench.workloads.Workload;
import edu.buffalo.itembench.workloads.ragnarok_rfid.RagNotificationWorkload;
import edu.buffalo.itembench.workloads.ragnarok_rfid.RagSmartNotificationWorkload;
import edu.buffalo.itembench.workloads.ragnarok_rfid.RagWriteOnlyWorkload;
import edu.buffalo.itembench.workloads.rfid.SmartNotificationWorkload;

/**
 * @author pketki
 * 
 */
public class Runner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DbConnector dbConn = null;
		try {
                    
			dbConn = new DbConnector();
			SimpleTest test = new SimpleTest(dbConn);
                        
			//Workload workload = new WriteOnlyWorkload();
			Workload workload = new RagSmartNotificationWorkload();//AuthenticationWorkload();
                        
			 for (int i = 0; i < 3; i++) {
				test.run(workload);
				MetricsBean metrics = test.getMetrics();
				System.out.println(metrics.toString());
			 }
			//TODO: write metrics to file

		} catch (DbException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (dbConn != null) {
					dbConn.closeConnection();
				}
			}catch (DbException e){
				e.printStackTrace();
			}
			System.exit(0);
		}
	}

}
