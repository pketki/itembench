/**
 * Workload to emulate read-only applications 
 * like an authenticating RFID or local cache 
 */
package edu.buffalo.itembench.workloads;

/**
 * @author pketki
 *
 */
public class ReadOnlyWorkload extends Workload {

	/**
	 * 
	 */
	public ReadOnlyWorkload() {
		this.setReadLoad(100);
	}

	/**
	 * @param readLoad
	 * @param writeLoad
	 * @param updateLoad
	 */
	public ReadOnlyWorkload(int readLoad, int writeLoad, int updateLoad) {
		this();
	}

	@Override
	public void init() {
		// TODO: Create table & insert/import rows

	}

	@Override
	public void run() {
		// TODO: Fire Select queries at particular intervals

	}

}
