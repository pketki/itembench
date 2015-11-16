/**
 * 
 */
package edu.buffalo.itembench.db;

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

	public ColumnDescriptor(DataType type, boolean unique, Integer min,
			Integer max, String valuePool) {
		super();
		this.type = type;
		this.unique = unique;
		this.min = min;
		this.max = max;
		this.valuePool = valuePool;
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

}
