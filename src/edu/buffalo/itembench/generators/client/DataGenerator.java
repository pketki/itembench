/**
 * 
 */
package edu.buffalo.itembench.generators.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.OperationNotSupportedException;

import edu.buffalo.itembench.db.ColumnDescriptor;

/**
 * @author pketki
 *
 */
public class DataGenerator {
	private Map<String, ColumnDescriptor> schema;
	private int number = 0;
	private int rangeStart = 0;
	private int rangeEnd = 10000;
	private List<String> stringPool;

	public void setSchema(Map<String, ColumnDescriptor> schema) {
		this.schema = schema;
		try {
			stringPool = Files.readAllLines(Paths.get("resources/generic.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Object> getRow() throws OperationNotSupportedException,
			IOException {
		List<Object> row = new ArrayList<Object>();
		for (Entry<String, ColumnDescriptor> column : schema.entrySet()) {
			ColumnDescriptor value = column.getValue();
			switch (value.getType()) {
			case BLOB:
				throw new OperationNotSupportedException();
			case DATETIME:
				Long now = new Date().getTime();
				row.add(new java.sql.Date(now));
				break;
			case NUMBER:
				if (value.getMin() != null)
					rangeStart = value.getMin();
				row.add(generateNumber(value));
				break;
			case TEXT:
				row.add(generateString(value));
				break;
			default:
				throw new OperationNotSupportedException();
			}
		}
		return row;
	}

	private int getRandomNumber(int min, int max) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}

	private int generateNumber(ColumnDescriptor column) {
		int num = 0, min, max;
		min = column.getMin() == null ? rangeStart : column.getMin();
		max = column.getMin() == null ? rangeEnd : column.getMin();
		if (column.isUnique())
			num = min + number++;
		else
			num = getRandomNumber(min, max);
		return num;
	}

	private String generateString(ColumnDescriptor column) throws IOException {
		String str = null;
		if (column.isUnique()) {
			str = "STR_" + number;
		} else {
			List<String> pool = null;
			if (column.getValuePool() != null) {
				pool = Files.readAllLines(Paths.get(column.getValuePool()));
			} else {
				pool = stringPool;
			}
			str = pool.get(getRandomNumber(0, pool.size() - 1));
		}
		return str;
	}
}
