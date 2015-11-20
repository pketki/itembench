package edu.buffalo.itembench.generators;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.random.MersenneTwister;

/**
 * Created by abhinit on 11/15/15.
 */
public class GaussianGenerator implements Generator {
	private MersenneTwister randomGenerator = null;
	private AbstractRealDistribution generator = null;
	private double mean = 0;
	private double standardDev = 1;

	GaussianGenerator() {
		randomGenerator = new MersenneTwister();
		this.generator = new NormalDistribution(randomGenerator, mean,
				standardDev);
	}

	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public double getStandardDev() {
		return standardDev;
	}

	public void setStandardDev(double standardDev) {
		this.standardDev = standardDev;
	}

	@Override
	public String getNextString(int length) {
		return null;
	}

	@Override
	public String getNextString(String valuePool) throws IOException {
		return null;
	}

	@Override
	public int getNextInt() {
		return 0;
	}

	@Override
	public int getNextInt(int start, int end) {
		return 0;
	}

	@Override
	public double getNextDouble() {
		return generator.sample();
	}

	@Override
	public double getNextDouble(double start, double end) {
		return generator.sample();
	}

	@Override
	public Date getNextDate() {
		return null;
	}

	@Override
	public Date getNextDate(Date start, Date end, int interval) {
		return null;
	}
}
