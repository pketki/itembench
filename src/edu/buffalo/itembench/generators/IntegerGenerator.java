package edu.buffalo.itembench.generators;

/**
 * Created by abhinit on 11/10/15.
 */
public abstract class IntegerGenerator implements Generator{

    public abstract int nextInt();

    @Override
    public String getNextString(){
        return ""+nextInt();
    }
}
