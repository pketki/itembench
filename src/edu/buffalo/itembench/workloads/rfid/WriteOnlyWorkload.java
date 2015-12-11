/**
 * A 100% write workload which acts like a logger 
 * and records the position snapshot at a given
 * time for each hacker present in the conference
 */
package edu.buffalo.itembench.workloads.rfid;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;

import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.SigarException;

import edu.buffalo.itembench.db.ColumnDescriptor;
import edu.buffalo.itembench.generators.Distribution;
import edu.buffalo.itembench.generators.client.QueryGenerator;
import edu.buffalo.itembench.util.DataType;
import edu.buffalo.itembench.util.Helper;
import edu.buffalo.itembench.workloads.Workload;

/**
 * @author pketki
 */
public class WriteOnlyWorkload extends Workload {

	private String load;
	private String query;
	private Connection connection;

	public WriteOnlyWorkload() {

		this.setWriteLoad(100);

		schema = new LinkedHashMap<String, ColumnDescriptor>();
		schema.put("TIMESTAMP", new ColumnDescriptor(DataType.VARCHAR, false,
				null, null, null, Distribution.Series));
		schema.put("TAG_ID", new ColumnDescriptor(DataType.INT, false, 3000,
				6500, null, Distribution.Random));
		schema.put("AREA_ID", new ColumnDescriptor(DataType.VARCHAR, false,
				null, null, "resources/areas.txt", Distribution.Random));
	}

	@Override
	public void init(Connection dbConn) {
		connection = dbConn;
		QueryGenerator queryGen = new QueryGenerator();
		load = queryGen.getCreateQuery("POSITION_SNAPSHOT", schema);
		try {
			Statement statement = dbConn.createStatement();
			statement.execute(load);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run(Connection dbConn) throws IOException {
		connection = dbConn;
		setTotalOps(0);
		for (int i = 0; i < 5; i++) {
			loadData("POSITION_SNAPSHOT", (getWriteLoad() * 100), schema);
			try {
				Helper.memList.add(Helper.sg.getMem().getUsed() / 1024);
				ProcCpu nw = Helper.sg.getProcCpu(Helper.sg.getPid());
				Helper.cpuList.add(nw.getPercent() * 100 / Helper.cpuCount);

				// Insert a 5 second delay to relatively emulate a time where
				// people are busy attending talks and not moving much
				Thread.sleep(5000);

			} catch (SigarException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void close(Connection dbConn) {
		connection = dbConn;
		load = "DROP TABLE POSITION_SNAPSHOT";
		try {
			Statement statement = dbConn.createStatement();
			statement.execute(load);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
