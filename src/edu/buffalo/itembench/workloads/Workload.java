/**
 * 
 */
package edu.buffalo.itembench.workloads;

import java.sql.Connection;

/**
 * @author jlimaye
 * 
 */
public abstract class Workload {
	private int readLoad;
	private int writeLoad;
	private int updateLoad;

	public Workload() {
		super();
	}

	/**
	 * @param readLoad
	 * @param writeLoad
	 * @param updateLoad
	 */
	public Workload(int readLoad, int writeLoad, int updateLoad) {
		this();
		this.setReadLoad(readLoad);
		this.setWriteLoad(writeLoad);
		this.setUpdateLoad(updateLoad);
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

	public int getUpdateLoad() {
		return updateLoad;
	}

	public void setUpdateLoad(int updateLoad) {
		this.updateLoad = updateLoad;
	}

	public abstract void init(Connection dbConn);

	public abstract void run(Connection dbConn);
}
