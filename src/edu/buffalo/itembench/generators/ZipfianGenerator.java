package edu.buffalo.itembench.generators;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.apache.commons.math3.distribution.ZipfDistribution;
import org.apache.commons.math3.random.MersenneTwister;

/**
 * Created by abhinit on 11/15/15.
 */
public class ZipfianGenerator implements Generator {
	private MersenneTwister randomGenerator = new MersenneTwister();
	private ZipfDistribution numberGenerator = null;
	private ZipfDistribution stringGenerator = null;
	private int numberOfElements = 10;

	private double mean = 1;

	ZipfianGenerator() {
		// this.generator = new ZipfDistribution(randomGenerator,
		// numberOfElements, mean);
	}

	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	@Override
	public String getNextString(int length) {
		return null;
	}

	@Override
	public String getNextString(String valuePool) throws IOException {
		List<String> pool = Files.readAllLines(Paths.get(valuePool));
		if (stringGenerator == null)
			stringGenerator = new ZipfDistribution(pool.size(), 2);
		return pool.get(stringGenerator.sample());
	}

	@Override
	public int getNextInt() {
		return getNextInt(0, 99);
	}

	@Override
	public int getNextInt(int start, int end) {
		if (numberGenerator == null)
			numberGenerator = new ZipfDistribution(end - start, 2);
		return numberGenerator.sample() + start;
	}

	@Override
	public double getNextDouble() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getNextDouble(double start, double end) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Date getNextDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getNextDate(Date start, Date end, int interval) {
		// TODO Auto-generated method stub
		return null;
	}

}
