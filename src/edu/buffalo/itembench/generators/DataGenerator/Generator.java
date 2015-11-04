package edu.buffalo.itembench.generators.DataGenerator;


import org.apache.commons.math3.distribution.AbstractIntegerDistribution;
import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.ZipfDistribution;
import org.apache.commons.math3.random.MersenneTwister;

/**
 * Created by abhinit on 11/3/15.
 */
public class Generator {
    private MersenneTwister randomGenerator = new MersenneTwister();
    private AbstractRealDistribution generator = null;
    private AbstractIntegerDistribution integerDistribution = null;
    private int numberOfElements = 1000;
    private double mean = 1;

    Generator(){
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public void setGenerator(Distribution distribution) throws InvalidDistribution{
        switch (distribution){
            case Exponential:
                this.generator = new ExponentialDistribution(randomGenerator,mean);
                break;
            case Zipfian:
                this.integerDistribution = new ZipfDistribution(randomGenerator,numberOfElements,mean);
                break;
            default: throw new InvalidDistribution();
        }
    }

    public Double generateNumeric(){
        return generator.sample();
    }

    public int  generateZipfian(){
        return integerDistribution.sample();
    }
}
