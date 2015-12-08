package edu.buffalo.itembench.generators;

/**
 * Created by abhinit on 11/15/15.
 */
public class GeneratorFactory {
	private static final GeneratorFactory factoryInstance = new GeneratorFactory();

	public static GeneratorFactory getInstance() {
		return factoryInstance;
	}

	public Generator getGenerator(Distribution distribution)
			throws InvalidDistribution {
		Generator instance = null;
		if (distribution == Distribution.Series) {
			instance = new SeriesGenerator();
			return instance;
		} else if (distribution == Distribution.Gaussian) {
			instance = new GaussianGenerator();
			return instance;
		} else if (distribution == Distribution.Zipfian) {
			instance = new ZipfianGenerator();
			return instance;
		} else if (distribution == Distribution.Random) {
			instance = new RandomGenerator();
			return instance;
		} else
			throw new InvalidDistribution();

	}

}
