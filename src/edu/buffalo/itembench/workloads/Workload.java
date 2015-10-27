/**
 * 
 */
package edu.buffalo.itembench.workloads;

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
		this.readLoad = readLoad;
		this.writeLoad = writeLoad;
		this.updateLoad = updateLoad;
	}

	public abstract void run();
}
