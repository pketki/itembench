/**
 * 
 */
package edu.buffalo.itembench.generators;

import java.util.List;
import java.util.Map;

import edu.buffalo.itembench.util.DataType;

/**
 * @author jlimaye
 *
 */
public interface ValueGenerator {
	
	public void setSchema (Map<String, DataType> schema);
	
	public List<String> getRow();
}
