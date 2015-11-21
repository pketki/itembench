/**
 * 
 */
package edu.buffalo.itembench.generators.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.OperationNotSupportedException;

import org.apache.commons.lang3.time.DateUtils;

import edu.buffalo.itembench.db.ColumnDescriptor;
import edu.buffalo.itembench.generators.Generator;
import edu.buffalo.itembench.generators.GeneratorFactory;
import edu.buffalo.itembench.generators.InvalidDistribution;

/**
 * @author pketki
 *
 */
public class DataGenerator {
	private Map<String, ColumnDescriptor> schema;
	private Generator numberGenerator;
	private Generator stringGenerator;
	private Generator dateGenerator;

	public void setSchema(Map<String, ColumnDescriptor> schema) {
		this.schema = schema;
	}

	public List<Object> getRow() throws OperationNotSupportedException,
			IOException, InvalidDistribution {
		List<Object> row = new ArrayList<Object>();
		for (Entry<String, ColumnDescriptor> column : schema.entrySet()) {
			ColumnDescriptor value = column.getValue();
			switch (value.getType()) {
			case BLOB:
				throw new OperationNotSupportedException();
			case DATETIME:
				if (dateGenerator == null)
					dateGenerator = GeneratorFactory.getInstance()
							.getGenerator(value.getDistribution());
				Long now = dateGenerator.getNextDate(new Date(),
						DateUtils.addDays(new Date(), -2), 1).getTime();
				row.add(new java.sql.Date(now));
				break;
			case INT:
			case NUMBER:
				if (numberGenerator == null)
					numberGenerator = GeneratorFactory.getInstance()
							.getGenerator(value.getDistribution());
				if (value.getMin() != null)
					row.add(numberGenerator.getNextInt(value.getMin(),
							value.getMax()));
				else
					row.add(numberGenerator.getNextInt());
				break;
			case VARCHAR:
			case TEXT:
				if (stringGenerator == null)
					stringGenerator = GeneratorFactory.getInstance()
							.getGenerator(value.getDistribution());
				if (value.getValuePool() == null)
					row.add(stringGenerator.getNextString(25));
				else
					row.add(stringGenerator.getNextString(value.getValuePool()));
				break;
			default:
				throw new OperationNotSupportedException();
			}
		}
		return row;
	}
}
