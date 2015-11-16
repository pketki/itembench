package edu.buffalo.itembench.generators.DataGenerator;

import edu.buffalo.itembench.generators.Generator;
import edu.buffalo.itembench.generators.ValueGenerator;
import edu.buffalo.itembench.util.DataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by abhinit on 11/3/15.
 */
public class DataGeneratorClient implements ValueGenerator{
    private ArrayList<Generator> generatorList = null;
    private int tupleSize = 10;
    private Distribution distribution = null;
    private Map<String,DataType> schema = null;
    private GeneratorFactory generatorFactory = GeneratorFactory.getInstance();


    public DataGeneratorClient(){
        this.generatorList = new ArrayList<>();

    }


    @Override
    public void setSchema(Map<String, DataType> schema) {
        this.schema = schema;
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
            generator = generatorFactory.getGenerator(distribution);
            generatorList.add(generator);
        }
    }

    @Override
    public List<String> getRow() {

        List<String> tupleList = new ArrayList<>();

        int index = 0;

        for(Generator generator : generatorList){
            tupleList.add(generator.getNextString());
            index++;
        }

        return tupleList;
    }



    public static void main(String[] args){
        DataGeneratorClient client  = new DataGeneratorClient();
        try {
            client.setDefaultEnv();
            List<String> tupleList = client.getRow();

            for(String elem : tupleList){
                System.out.println(elem);
            }


        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

}
