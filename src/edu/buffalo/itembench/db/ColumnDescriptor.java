/**
 * 
 */
package edu.buffalo.itembench.db;

import edu.buffalo.itembench.generators.Distribution;
import edu.buffalo.itembench.util.DataType;

/**
 * @author pketki
 *
 */
public class ColumnDescriptor {

	private DataType type;
	private boolean unique;
	private Integer min;
	private Integer max;
	private String valuePool;
	private Distribution distribution;

	public ColumnDescriptor(DataType type, boolean unique, Integer min,
			Integer max, String valuePool, Distribution distribution) {
		super();
		this.type = type;
		this.unique = unique;
		this.min = min;
		this.max = max;
		this.valuePool = valuePool;
		this.distribution = distribution;
	}

	public DataType getType() {
		return type;
	}

	public boolean isUnique() {
		return unique;
	}

	public Integer getMin() {
		return min;
	}

	public Integer getMax() {
		return max;
	}

	public String getValuePool() {
		return valuePool;
	}

	public Distribution getDistribution() {
		return distribution;
	}

	public void setDistribution(Distribution distribution) {
		this.distribution = distribution;
	}

}
