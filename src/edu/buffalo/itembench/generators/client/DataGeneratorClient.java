package edu.buffalo.itembench.generators.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.buffalo.itembench.db.ColumnDescriptor;
import edu.buffalo.itembench.generators.Distribution;
import edu.buffalo.itembench.generators.Generator;
import edu.buffalo.itembench.generators.GeneratorFactory;
import edu.buffalo.itembench.generators.InvalidDistribution;

/**
 * Created by abhinit on 11/3/15.
 */
public class DataGeneratorClient {
	private static final DataGeneratorClient instance = new DataGeneratorClient();
	private GeneratorFactory generatorFactory = GeneratorFactory.getInstance();

	private Map<String, ColumnDescriptor> schema;
	private ArrayList<Generator> generatorList = new ArrayList<>();
	private int tupleSize = 10;
	private Distribution distribution = null;
	private List<String> stringPool;

	private DataGeneratorClient() {
	}

	public static DataGeneratorClient getInstance() {
		return instance;
	}

	public void setSchema(Map<String, ColumnDescriptor> schema) {
		this.schema = schema;
		try {
			stringPool = Files.readAllLines(Paths.get("resources/generic.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**********************************
	 * Properties consist of parameters: type of distribution Number of samples
	 * Tuple Size
	 ***********************************/
	public void setEnv(Properties properties) {

	}

	public void setDefaultEnv() throws InvalidDistribution {
		distribution = Distribution.Zipfian;
		setTupleSize(this.tupleSize);
	}

	private void setTupleSize(int tupleSize) throws InvalidDistribution {
		this.tupleSize = tupleSize;
		initGenerators();
	}

	private void initGenerators() throws InvalidDistribution {
		Generator generator = null;
		ColumnDescriptor descriptor = null;

		for (int index = 0; index < this.tupleSize; index++) {
			generator = generatorFactory.getGenerator(distribution);
			generatorList.add(generator);
		}
	}

	public List<Object> getRow() {

		List<Object> tupleList = new ArrayList<>();

		int index = 0;

		for (Generator generator : generatorList) {
			// tupleList.add(generator.getNextString());
			index++;
		}

		return tupleList;
	}

	public static void main(String[] args) {
		DataGeneratorClient client = new DataGeneratorClient();
		try {
			client.setDefaultEnv();
			List<Object> tupleList = client.getRow();

			for (Object elem : tupleList) {
				System.out.println(elem);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}