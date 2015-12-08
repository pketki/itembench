/**
 * 
 */
package edu.buffalo.itembench.generators;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * @author pketki
 *
 */
public class RandomGenerator implements Generator {

	@Override
	public String getNextString(int sampleLength) {
		return RandomStringUtils.random(sampleLength);
	}

	@Override
	public int getNextInt() {
		return getNextInt(0, 9999);
	}

	@Override
	public double getNextDouble() {
		return getNextDouble(0.0, 99.99);
	}

	@Override
	public Date getNextDate() {
		return new Date(RandomUtils.nextLong(0, 999999));
	}

	@Override
	public String getNextString(String valuePool) throws IOException {
		List<String> pool = Files.readAllLines(Paths.get(valuePool));
		return pool.get(getNextInt(0, pool.size() - 1));
	}

	@Override
	public int getNextInt(int start, int end) {
		return RandomUtils.nextInt(start, end);
	}

	@Override
	public double getNextDouble(double start, double end) {
		return RandomUtils.nextDouble(start, end);
	}

	@Override
	public Date getNextDate(Date start, Date end, int interval) {
		long diff = (end.getTime() - start.getTime()) / (60 * 60 * 1000);
		Date date = DateUtils.addHours(start,
				interval * getNextInt(0, (int) diff));
		return date;
	}

}
