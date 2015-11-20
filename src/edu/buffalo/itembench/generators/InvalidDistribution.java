package edu.buffalo.itembench.generators;

/**
 * Created by abhinit on 11/3/15.
 */
public class InvalidDistribution extends Exception{
    private static final String errorMessage = "Invalid distribution";

    InvalidDistribution(){
        super(errorMessage);
    }
}
