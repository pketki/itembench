/**
 * 
 */
package edu.buffalo.itembench.workloads.rfid;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.naming.OperationNotSupportedException;

import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.SigarException;

import edu.buffalo.itembench.db.ColumnDescriptor;
import edu.buffalo.itembench.generators.Distribution;
import edu.buffalo.itembench.generators.InvalidDistribution;
import edu.buffalo.itembench.generators.client.DataGenerator;
import edu.buffalo.itembench.generators.client.QueryGenerator;
import edu.buffalo.itembench.util.DataType;
import edu.buffalo.itembench.util.Helper;
import edu.buffalo.itembench.workloads.Workload;

/**
 * @author Warren
 *
 */
public class SmartNotificationWorkload extends Workload {

	private Connection connection;
	private ScheduledExecutorService scheduler = Executors
			.newSingleThreadScheduledExecutor();

	/**
	 * 
	 */
	public SmartNotificationWorkload() {
		setReadLoad(40);
		setWriteLoad(60);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.buffalo.itembench.workloads.Workload#init(java.sql.Connection)
	 */
	@Override
	public void init(Connection dbConn) {

		QueryGenerator queryGen = new QueryGenerator();

		Map<String, ColumnDescriptor> schema1 = new LinkedHashMap<String, ColumnDescriptor>();
		schema1.put("ATTENDEE_ID", new ColumnDescriptor(DataType.INT, false,
				3000, 6500, null, Distribution.Random));
		schema1.put("INTEREST", new ColumnDescriptor(DataType.VARCHAR, false,
				null, null, "resources/interests.txt", Distribution.Random));

		String load = queryGen.getCreateQuery("AUTHORIZED_USER", schema1);

		Map<String, ColumnDescriptor> schema2 = new LinkedHashMap<String, ColumnDescriptor>();
		schema2.put("HACKER_ID", new ColumnDescriptor(DataType.INT, true, 3000,
				6500, null, Distribution.Random));
		schema2.put("INTEREST", new ColumnDescriptor(DataType.VARCHAR, false,
				null, null, "resources/interests.txt", Distribution.Random));

		String load1 = queryGen.getCreateQuery("INTEREST_LIST", schema2);

		try {
			Statement statement = dbConn.createStatement();
			statement.execute(load);
			statement.execute(load1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String query = "INSERT INTO AUTHORIZED_USER VALUES(?,?)";
		String query1 = "INSERT INTO INTEREST_LIST VALUES(?,?)";

		try {
			dbConn.setAutoCommit(false);
			PreparedStatement statement = dbConn.prepareStatement(query1);
			DataGenerator generator = new DataGenerator();
			generator.setSchema(schema2);
			List<Object> row;

			// Initialize registered users table
			int insertLimit = 100;
			for (int i = 0; i < insertLimit; i++) {
				row = generator.getRow();
				int idx = 1;
				for (Object value : row) {
					statement.setObject(idx, value);
					idx++;
				}
				statement.addBatch();
			}
			statement.executeBatch();
			dbConn.commit();

			// Initialize authenticated user
			statement = dbConn.prepareStatement(query);
			generator = new DataGenerator();
			generator.setSchema(schema1);
			row = generator.getRow();
			int idx = 1;
			for (Object value : row) {
				statement.setObject(idx, value);
				idx++;
			}
			statement.addBatch();

			statement.executeBatch();
			dbConn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (OperationNotSupportedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidDistribution e) {
			e.printStackTrace();
		}
		schema = new LinkedHashMap<String, ColumnDescriptor>();
		schema.put("TIMESTAMP", new ColumnDescriptor(DataType.TIMESTAMP, false,
				null, null, null, Distribution.Series));
		schema.put("TAG_ID", new ColumnDescriptor(DataType.INT, false, 3000,
				6500, null, Distribution.Random));
		schema.put("AREA", new ColumnDescriptor(DataType.VARCHAR, false, null,
				null, "resources/areas.txt", Distribution.Random));
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

		Runnable runnable = new Runnable() {
			public void run() {
				try {
					if (connection != null && !connection.isClosed())
						readData();
				} catch (SQLException e) {
					System.out.println("closed");
				}

			}
		};
		ScheduledFuture<?> readHandle = scheduler.scheduleAtFixedRate(runnable,
				(getWriteLoad() / 10), (getReadLoad() / 10), TimeUnit.SECONDS);
		setTotalOps(0);
		for (int i = 0; i < 5; i++) {
			writeData();
			// readData();
			try {
				Helper.memList.add(Helper.sg.getMem().getUsed() / 1024);
				ProcCpu nw = Helper.sg.getProcCpu(Helper.sg.getPid());
				Helper.cpuList.add(nw.getPercent() * 100 / Helper.cpuCount);
			} catch (SigarException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		readHandle.cancel(true);
	}

	private void writeData() {
		String query = "INSERT INTO POSITION_SNAPSHOT VALUES (?,?,?)";
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(query);
			DataGenerator generator = new DataGenerator();
			generator.setSchema(schema);
			List<Object> row;
			int insertLimit = getWriteLoad() * 300;
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readData() {
		System.out.println("here");
		String query = "SELECT INTEREST FROM "
				+ "(SELECT * FROM POSITION_SNAPSHOT "
				+ "WHERE AREA = 'Hopper') P, INTEREST_LIST L "
				+ "WHERE P.TAG_ID = L.HACKER_ID " + "GROUP BY INTEREST "
				+ "HAVING COUNT(TAG_ID) = (SELECT MAX(CNT) "
				+ "FROM (SELECT COUNT(TAG_ID) AS CNT, INTEREST "
				+ "FROM POSITION_SNAPSHOT A, INTEREST_LIST B "
				+ "WHERE A.TAG_ID = B.HACKER_ID " + "AND A.AREA = 'Hopper' "
				+ "GROUP BY INTEREST))";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			java.util.Date now = new java.util.Date();

			ResultSet result = statement.executeQuery();
			while (result.next())
				System.out.println(result.getString(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void close(Connection dbConn) {
		try {
			scheduler.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		connection = dbConn;
		String query1 = "DROP TABLE POSITION_SNAPSHOT";
		String query2 = "DROP TABLE AUTHORIZED_USER";
		String query3 = "DROP TABLE INTEREST_LIST";
		try {
			Statement statement = dbConn.createStatement();
			statement.execute(query1);
			statement.execute(query2);
			statement.execute(query3);
			dbConn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
