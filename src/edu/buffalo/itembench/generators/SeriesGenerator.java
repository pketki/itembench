/**
 * 
 */
package edu.buffalo.itembench.generators;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

/**
 * @author Ketki
 *
 */
public class SeriesGenerator implements Generator {
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.buffalo.itembench.generators.Generator#getNextString(int)
	 */
	private StringBuilder str;
	private int cntr = 0;
	private int integer = 0;
	private double decimal = 0.0;

	@Override
	public String getNextString(int length) {
		str = new StringBuilder("STR_");
		return str.append(cntr++).toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.buffalo.itembench.generators.Generator#getNextString(java.lang.String
	 * )
	 */
	@Override
	public String getNextString(String valuePool) throws IOException {
		List<String> pool = Files.readAllLines(Paths.get(valuePool));
		if (cntr >= pool.size())
			cntr = 0;
		return pool.get(cntr++);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.buffalo.itembench.generators.Generator#getNextInt()
	 */
	@Override
	public int getNextInt() {
		return integer++;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.buffalo.itembench.generators.Generator#getNextInt(int, int)
	 */
	@Override
	public int getNextInt(int start, int end) {
		return start + integer++;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.buffalo.itembench.generators.Generator#getNextDouble()
	 */
	@Override
	public double getNextDouble() {
		return decimal += 0.1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.buffalo.itembench.generators.Generator#getNextDouble(double,
	 * double)
	 */
	@Override
	public double getNextDouble(double start, double end) {
		return start + (decimal += 0.1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.buffalo.itembench.generators.Generator#getNextDate()
	 */
	@Override
	public Date getNextDate() {
		return new Date();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.buffalo.itembench.generators.Generator#getNextDate(java.util.Date,
	 * java.util.Date, int)
	 */
	@Override
	public Date getNextDate(Date start, Date end, int interval) {
		return DateUtils.addMinutes(start, interval);
	}

}
