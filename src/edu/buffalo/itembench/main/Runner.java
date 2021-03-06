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
import edu.buffalo.itembench.workloads.rfid.NotificationWorkload;
import edu.buffalo.itembench.workloads.rfid.SmartNotificationWorkload;
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
		DbConnector dbConn = null;
		try {
                    
			dbConn = new DbConnector();
			SimpleTest test = new SimpleTest(dbConn);

//			Workload workload = new WriteOnlyWorkload();
//			Workload workload = new SmartNotificationWorkload();//AuthenticationWorkload();
			Workload workload = new NotificationWorkload();

                        
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
