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
public class AuthenticationWorkload extends Workload {

	private Connection connection;

	/**
	 * 
	 */
	public AuthenticationWorkload() {
		setReadLoad(54);
		setWriteLoad(46);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.buffalo.itembench.workloads.Workload#init(java.sql.Connection)
	 */
	@Override
	public void init(Connection dbConn) {
		connection = dbConn;
		schema = new LinkedHashMap<String, ColumnDescriptor>();
		schema.put("TAG_ID", new ColumnDescriptor(DataType.INT, false, 3000,
				6500, null, Distribution.Series));
		String load = "CREATE TABLE VALID_TAGS (";
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
		String query = "INSERT INTO VALID_TAGS VALUES(?)";
		try {
			dbConn.setAutoCommit(false);
			PreparedStatement statement = dbConn.prepareStatement(query);
			DataGenerator generator = new DataGenerator();
			generator.setSchema(schema);
			List<Object> row;
			int insertLimit = 3500;
			setTotalOps(getTotalOps() + insertLimit);
			for (int i = 0; i < insertLimit; i++) {
				row = generator.getRow();
				int idx = 1;
				for (Object value : row) {
					statement.setObject(idx, value);
					idx++;
				}
				statement.execute();
			}
			dbConn.commit();

			query = "DELETE FROM VALID_TAGS WHERE TAG_ID = ?";
			statement = dbConn.prepareStatement(query);
			schema = new LinkedHashMap<String, ColumnDescriptor>();
			schema.put("TAG_ID", new ColumnDescriptor(DataType.INT, false,
					3000, 6500, null, Distribution.Random));
			generator = new DataGenerator();
			generator.setSchema(schema);
			for (int i = 0; i < 10; i++) {
				row = generator.getRow();
				statement.setObject(1, row.get(0));
				statement.execute();
			}
			dbConn.commit();
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

	@Override
	public void run(Connection dbConn) throws IOException {
		schema = new LinkedHashMap<String, ColumnDescriptor>();
		schema.put("TIMESTAMP", new ColumnDescriptor(DataType.VARCHAR, false,
				null, null, null, Distribution.Series));
		schema.put("TAG_ID", new ColumnDescriptor(DataType.INT, false, 3000,
				6500, null, Distribution.Random));
		schema.put("AREA_ID", new ColumnDescriptor(DataType.VARCHAR, false, null,
				null, "resources/areas.txt", Distribution.Random));

		String load1 = "CREATE TABLE POSITION_SNAPSHOT(";
		for (Entry<String, ColumnDescriptor> column : schema.entrySet()) {
			load1 += column.getKey() + " " + column.getValue().getType();
			if(column.getValue().getType() == DataType.INT || column.getValue().getType() == DataType.NUMBER){
				load1 += ", ";
			} else {
				load1 += "(255), ";
			}
		}
		load1 = load1.substring(0, load1.lastIndexOf(','));
		load1 += ")";
		try {
			Statement statement = dbConn.createStatement();
			statement.execute(load1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connection = dbConn;
		
		setTotalOps(0);
		for(int i=0;i<5;i++){
			writeData();
			try {
				Helper.memList.add(Helper.sg.getMem().getUsed()/1024);
				ProcCpu nw = Helper.sg.getProcCpu(Helper.sg.getPid());
				Helper.cpuList.add(nw.getPercent()*100/Helper.cpuCount);
			} catch (SigarException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
//		writeData();
//		writeData();
//		writeData();
//		writeData();
	}

	private boolean readData(Object id) {
		String query = "SELECT TAG_ID FROM VALID_TAGS WHERE TAG_ID = " + id;
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			return (result.getFetchSize() != 0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
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
				if (readData(row.get(1))) {
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
			}
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (OperationNotSupportedException e) {
			e.printStackTrace();
		} catch (InvalidDistribution e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void close(Connection dbConn) {
		connection = dbConn;
		String query1 = "DROP TABLE POSITION_SNAPSHOT";
		String query2 = "DROP TABLE VALID_TAGS";
		try {
			Statement statement = dbConn.createStatement();
			statement.execute(query1);
			statement.execute(query2);
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
}
