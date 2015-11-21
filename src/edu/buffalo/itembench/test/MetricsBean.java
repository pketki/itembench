/**
 * 
 */
package edu.buffalo.itembench.test;

import java.io.Serializable;

/**
 * @author pketki
 *
 */
public class MetricsBean implements Serializable {
	private Float runTime;
	private Float throughput;
	private Float latency;
	private Double cpuUsage;
	private Long memoryUsage;

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

	public Long getMemoryUsage() {
		return memoryUsage;
	}

	public void setMemoryUsage(Long memoryUsage) {
		this.memoryUsage = memoryUsage;
	}

	@Override
	public String toString() {
		return "MetricsBean [Overall RunTime(ms)=" + runTime + ", Throughput=" + throughput
				+ ", Average Latency(ms)=" + latency + ", Cpu Usage=" + cpuUsage
				+ "%, Memory Usage(kb)=" + memoryUsage + "]";

	}

}
