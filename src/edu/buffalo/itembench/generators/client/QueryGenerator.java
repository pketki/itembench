/**
 * 
 */
package edu.buffalo.itembench.generators.client;

import java.util.Map;
import java.util.Map.Entry;

import edu.buffalo.itembench.db.ColumnDescriptor;
import edu.buffalo.itembench.util.DataType;

/**
 * @author jlimaye
 *
 */
public class QueryGenerator {

	public String getCreateQuery(String tableName,
			Map<String, ColumnDescriptor> schema) {
		StringBuilder query = new StringBuilder();
		query.append("CREATE TABLE ");
		query.append(tableName);
		query.append("(");
		for (Entry<String, ColumnDescriptor> column : schema.entrySet()) {
			query.append(column.getKey());
			query.append(" ");
			query.append(column.getValue().getType());
			if (column.getValue().getType() != DataType.TEXT
					&& column.getValue().getType() != DataType.VARCHAR) {
				query.append(", ");
			} else {
				query.append("(255), ");
			}
		}
		query.deleteCharAt(query.lastIndexOf(","));
		query.append(")");
		return query.toString();
	}
}
