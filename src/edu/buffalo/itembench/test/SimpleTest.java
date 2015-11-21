/**
 * 
 */
package edu.buffalo.itembench.test;

import java.io.IOException;
import java.sql.Connection;
import java.util.Date;

import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

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

	public MetricsBean getMetrics() {
		return metrics;
	}

	public void run(Workload workload) throws DbException {
		Sigar sigar = new Sigar();
		int cpuCount = 0;
		
		Connection connection = dbConn.getConnection();
		workload.init(connection);
		dbConn.closeConnection();

		Long start = new Date().getTime();
		connection = dbConn.getConnection();
		ProcCpu old = null;
		long oldMem = 0;
		try {
			cpuCount = sigar.getCpuList().length;
			old = sigar.getProcCpu(sigar.getPid());
			oldMem = sigar.getMem().getUsed()/1024;
			workload.run(connection);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SigarException e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConnection();

			long runTime = new Date().getTime() - start;
			ProcCpu nw = null;
			long newMem = 0;
			try {
				nw = sigar.getProcCpu(sigar.getPid());
				newMem = sigar.getMem().getUsed()/1024;
			} catch (SigarException e) {
				e.printStackTrace();
			}
			
			metrics.setRunTime((float)runTime);
			metrics.setLatency(metrics.getRunTime()/workload.getTotalOps());
			metrics.setThroughput(workload.getTotalOps()*1000/metrics.getRunTime());
			metrics.setCpuUsage(nw.getPercent()*100/cpuCount);
			metrics.setMemoryUsage(newMem - oldMem);
			
			connection = dbConn.getConnection();
			workload.close(connection);
			dbConn.closeConnection();
		}		
	}

}
