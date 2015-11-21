/**
 * 
 */
package edu.buffalo.itembench.workloads;

import java.io.IOException;
import java.sql.Connection;
import java.util.Map;

import edu.buffalo.itembench.db.ColumnDescriptor;

/**
 * @author jlimaye
 * 
 */
public abstract class Workload {
	private int readLoad;
	private int writeLoad;
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
	
	public abstract void close(Connection dbConn);
}
