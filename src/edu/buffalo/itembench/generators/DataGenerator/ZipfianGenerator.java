package edu.buffalo.itembench.generators.DataGenerator;

import edu.buffalo.itembench.generators.IntegerGenerator;
import org.apache.commons.math3.distribution.ZipfDistribution;
import org.apache.commons.math3.random.MersenneTwister;

/**
 * Created by abhinit on 11/15/15.
 */
public class ZipfianGenerator extends IntegerGenerator{
    private MersenneTwister randomGenerator = new MersenneTwister();
    private ZipfDistribution generator = null;
    private int numberOfElements = 10;


    private double mean = 1;

    ZipfianGenerator(){
        this.generator = new ZipfDistribution(randomGenerator,numberOfElements,mean);
    }

    @Override
    public int nextInt() {
        return generator.sample();
    }


    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

}
