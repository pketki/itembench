/**
 * Workload to emulate read-only applications 
 * like an authenticating RFID or local cache 
 */
package edu.buffalo.itembench.workloads.ragnarok_rfid;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.naming.OperationNotSupportedException;

import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.SigarException;

import edu.buffalo.itembench.db.ColumnDescriptor;
import edu.buffalo.itembench.generators.Distribution;
import edu.buffalo.itembench.generators.InvalidDistribution;
import edu.buffalo.itembench.generators.client.DataGenerator;
import edu.buffalo.itembench.util.DataType;
import edu.buffalo.itembench.util.Helper;
import edu.buffalo.itembench.workloads.Workload;

/**
 * @author pketki
 *
 */
public class RagWriteOnlyWorkload extends Workload {

	private String load;
	private String query;
	private Connection connection;

	/**
	 * 
	 */
	public RagWriteOnlyWorkload() {
		this.setWriteLoad(100);
		schema = new LinkedHashMap<String, ColumnDescriptor>();
		schema.put("TIMESTAMP", new ColumnDescriptor(DataType.VARCHAR, false,
				null, null, null, Distribution.Series));
		schema.put("TAG_ID", new ColumnDescriptor(DataType.INT, false, 300000000,
				2000000000, null, Distribution.Random));
		schema.put("AREA_ID", new ColumnDescriptor(DataType.VARCHAR, false,
				null, null, "resources/areas.txt", Distribution.Random));
	}

	/**
	 * @param readLoad
	 * @param writeLoad
	 * @param updateLoad
	 */
	public RagWriteOnlyWorkload(int readLoad, int writeLoad, int updateLoad) {
		this();
	}

	@Override
	public void init(Connection dbConn) {
		connection = dbConn;
		load = "CREATE TABLE POSITION_SNAPSHOT (";
		for (Entry<String, ColumnDescriptor> column : schema.entrySet()) {
			load += column.getKey() + " " + column.getValue().getType();
			if (column.getValue().getType() == DataType.INT
					|| column.getValue().getType() == DataType.NUMBER) {
				load += ", ";
			} else {
				load += "(255), ";
			}
		}
		load = load.substring(0, load.lastIndexOf(','));
		load += ")";
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
			loadData();
			try {
				Helper.memList.add(Helper.sg.getMem().getUsed() / 1024);
				ProcCpu nw = Helper.sg.getProcCpu(Helper.sg.getPid());
				Helper.cpuList.add(nw.getPercent() * 100 / Helper.cpuCount);
			} catch (SigarException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// loadData();
		// loadData();
		// loadData();
		// loadData();
	}

	private void loadData() throws IOException {
		query = "INSERT INTO POSITION_SNAPSHOT VALUES (?,?,?)";
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(query);
			DataGenerator generator = new DataGenerator();
			generator.setSchema(schema);
			List<Object> row;
			int insertLimit = getWriteLoad() * 2000000000;
			setTotalOps(getTotalOps() + insertLimit);
			for (int i = 0; i < insertLimit; i++) {
				row = generator.getRow();
				int idx = 1;
				for (Object value : row) {
					if (value instanceof Date) {
						statement.setDate(idx, (Date) value);
					} else
						statement.setObject(idx, value);
					idx++;
				}
				statement.execute();
			}
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (OperationNotSupportedException e) {
			e.printStackTrace();
		} catch (InvalidDistribution e) {
			e.printStackTrace();
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
