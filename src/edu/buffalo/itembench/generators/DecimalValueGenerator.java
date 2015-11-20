package edu.buffalo.itembench.generators;

/**
 * Created by abhinit on 11/10/15.
 */
public abstract class DecimalValueGenerator implements Generator {
    public abstract double nextDecimalValue();

    @Override
    public String getNextString(){
        return ""+nextDecimalValue();
    }
}
