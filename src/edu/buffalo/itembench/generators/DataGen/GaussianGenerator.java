package edu.buffalo.itembench.generators.DataGen;

import edu.buffalo.itembench.generators.DecimalValueGenerator;
import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.random.MersenneTwister;

/**
 * Created by abhinit on 11/15/15.
 */
public  class GaussianGenerator extends DecimalValueGenerator{
    private MersenneTwister randomGenerator = null;
    private AbstractRealDistribution generator = null;
    private double mean = 0;
    private double standardDev  = 1;


    GaussianGenerator(){
        randomGenerator = new MersenneTwister();
        this.generator =  new NormalDistribution(randomGenerator,mean,standardDev);
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
