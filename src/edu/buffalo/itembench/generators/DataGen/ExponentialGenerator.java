package edu.buffalo.itembench.generators.DataGen;

import edu.buffalo.itembench.generators.DecimalValueGenerator;
import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.random.MersenneTwister;

/**
 * Created by abhinit on 11/15/15.
 */
public  class ExponentialGenerator extends DecimalValueGenerator{
    private MersenneTwister randomGenerator = null;
    private ExponentialDistribution generator = null;
    private double mean = 0;
    private double standardDev  = 1;


    ExponentialGenerator(){
        randomGenerator = new MersenneTwister();
        this.generator =  new ExponentialDistribution(randomGenerator,mean,standardDev);
    }

    @Override
    public double nextDecimalValue() {
        return randomGenerator.nextDouble();
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
}
