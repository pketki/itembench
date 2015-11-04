package edu.buffalo.itembench.generators.DataGenerator;

import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by abhinit on 11/3/15.
 */
public class DataGeneratorClient {
    private ArrayList<Generator> generatorList = null;
    private int tupleSize = 10;
    private Distribution distribution = null;



    public DataGeneratorClient(){
        this.generatorList = new ArrayList<>();

    }

    /**********************************
     Properties consist of parameters:
     * type of distribution
     * Number of samples
     * Tuple Size
    ***********************************/
    public void setEnv(Properties properties){

    }

    public void setDefaultEnv() throws InvalidDistribution{
        distribution = Distribution.Zipfian;
        setTupleSize(this.tupleSize);
    }


    private void setTupleSize(int tupleSize) throws InvalidDistribution{
        this.tupleSize  = tupleSize;
        initGenerators();
    }

    private void initGenerators() throws InvalidDistribution{
        Generator generator = null;
        for(int index=0;index<this.tupleSize;index++){
            generator = new Generator();
            generator.setGenerator(distribution);
            generatorList.add(generator);
        }
    }

    public int[] getZipfianTuple(){
        int[] tuple = new int[this.tupleSize];
        int index = 0;

        for(Generator generator : generatorList){
            tuple[index] = generator.generateZipfian();
            index++;
        }

        return tuple;
    }

    public double[] getTuple(){
        double[] tuple = new double[this.tupleSize];
        int index = 0;

        for(Generator generator : generatorList){
            tuple[index] = generator.generateNumeric();
            index++;
        }

        return tuple;
    }

    public static void main(String[] args){
        DataGeneratorClient client  = new DataGeneratorClient();
        try {
            client.setDefaultEnv();
            int[] tuple = client.getZipfianTuple();

            for(int elem : tuple){
                System.out.println(elem);
            }


        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

}
