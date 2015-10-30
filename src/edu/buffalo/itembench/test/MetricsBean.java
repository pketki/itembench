/**
 * 
 */
package edu.buffalo.itembench.test;

/**
 * @author pketki
 *
 */
public class MetricsBean {
	private Float runTime;
	private Float throughput;
	private Float latency;
	private Double cpuUsage;
	private Double memoryUsage;

	public Float getRunTime() {
		return runTime;
	}

	public void setRunTime(Float runTime) {
		this.runTime = runTime;
	}

	public Float getThroughput() {
		return throughput;
	}

	public void setThroughput(Float throughput) {
		this.throughput = throughput;
	}

	public Float getLatency() {
		return latency;
	}

	public void setLatency(Float latency) {
		this.latency = latency;
	}

	public Double getCpuUsage() {
		return cpuUsage;
	}

	public void setCpuUsage(Double cpuUsage) {
		this.cpuUsage = cpuUsage;
	}

	public Double getMemoryUsage() {
		return memoryUsage;
	}

	public void setMemoryUsage(Double memoryUsage) {
		this.memoryUsage = memoryUsage;
	}

}
