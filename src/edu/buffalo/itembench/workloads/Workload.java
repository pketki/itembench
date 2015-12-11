/**
 * 
 */
package edu.buffalo.itembench.workloads;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import edu.buffalo.itembench.db.ColumnDescriptor;
import edu.buffalo.itembench.generators.InvalidDistribution;
import edu.buffalo.itembench.generators.client.DataGenerator;
import edu.buffalo.itembench.generators.client.QueryGenerator;

/**
 * @author jlimaye
 * 
 */
public abstract class Workload {
	private int readLoad;
	private int writeLoad;
	protected Connection connection;
	protected Map<String, ColumnDescriptor> schema;
	private int totalOps = 0;

	// private int updateLoad;

	public Workload() {
		super();
	}

	public int getTotalOps() {
		return totalOps;
	}

	public void setTotalOps(int totalOps) {
		this.totalOps = totalOps;
	}

	public int getReadLoad() {
		return readLoad;
	}

	public void setReadLoad(int readLoad) {
		this.readLoad = readLoad;
	}

	public int getWriteLoad() {
		return writeLoad;
	}

	public void setWriteLoad(int writeLoad) {
		this.writeLoad = writeLoad;
	}

	// public int getUpdateLoad() {
	// return updateLoad;
	// }
	//
	// public void setUpdateLoad(int updateLoad) {
	// this.updateLoad = updateLoad;
	// }

	public abstract void init(Connection dbConn);

	public abstract void run(Connection dbConn) throws IOException;

	protected void loadData(String tableName, int insertLimit,
			Map<String, ColumnDescriptor> schema) {
		QueryGenerator queryGenerator = new QueryGenerator();

		String query = queryGenerator.getInsertQuery(tableName, schema);

		try {
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(query);
			DataGenerator generator = new DataGenerator();
			generator.setSchema(schema);
			List<Object> row;
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

	public abstract void close(Connection dbConn);
}
