/**
 * Workload to emulate read-only applications 
 * like an authenticating RFID or local cache 
 */
package edu.buffalo.itembench.workloads;

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

import edu.buffalo.itembench.db.ColumnDescriptor;
import edu.buffalo.itembench.generators.client.DataGenerator;
import edu.buffalo.itembench.util.DataType;

/**
 * @author pketki
 *
 */
public class RFIDWorkload extends Workload {

	private String load;
	private String query;
	private Connection connection;

	/**
	 * 
	 */
	public RFIDWorkload() {
		this.setReadLoad(10);
		this.setWriteLoad(90);
		schema = new LinkedHashMap<String, ColumnDescriptor>();
		schema.put("TIMESTAMP", new ColumnDescriptor(DataType.DATETIME, false,
				null, null, null));
		schema.put("TAG_ID", new ColumnDescriptor(DataType.NUMBER, false, 3000,
				6500, null));
		schema.put("AREA_ID", new ColumnDescriptor(DataType.TEXT, false, null,
				null, "resources/areas.txt"));
	}

	/**
	 * @param readLoad
	 * @param writeLoad
	 * @param updateLoad
	 */
	public RFIDWorkload(int readLoad, int writeLoad, int updateLoad) {
		this();
	}

	@Override
	public void init(Connection dbConn) {
		connection = dbConn;
		load = "CREATE TABLE POSITION_SNAPSHOT (";
		for (Entry<String, ColumnDescriptor> column : schema.entrySet()) {
			load += column.getKey() + " " + column.getValue().getType() + ", ";
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
		loadData();
		loadData();
		loadData();
		loadData();
		loadData();
	}

	private void loadData() throws IOException {
		query = "INSERT INTO POSITION_SNAPSHOT VALUES (?,?,?);";
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(query);
			DataGenerator generator = new DataGenerator();
			generator.setSchema(schema);
			List<Object> row;
			int insertLimit = getWriteLoad() * 10;
			setTotalOps(getTotalOps()+ insertLimit);
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
		}
	}

	private void readQuery() {
		query = "SELECT COUNT(TAG_ID), TRACK "
				+ "FROM POSITION_SNAPSHOT P, TALK_LIST L, TALKS T "
				+ "WHERE P.TAG_ID = L.HACKER_ID AND "
				+ "L.TALK_ID = T.TALK_ID GROUP BY TRACK;";
	}
}
