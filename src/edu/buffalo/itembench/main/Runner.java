/**
 * 
 */
package edu.buffalo.itembench.main;

import edu.buffalo.itembench.test.Test;
import java.nio.file.FileSystems;
import java.util.Scanner;

/**
 * @author pketki
 * 
 */
public class Runner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
            // TODO Auto-generated method stub
            System.out.println("Welcome to The ITEMBench Benchmark");
            
            Test test=new Test();
            //Integer for choice
            int sel=0;
            
            while(sel!=6){
            System.out.println("Please enter the number for the database you wish to use");
            System.out.println("1. SQLite");
            System.out.println("2. BerkeleyDb");
            System.out.println("3. ApacheDerby");
            System.out.println("4. H2");
            System.out.println("5. HSQLDb");
            System.out.println("6. Exit");
            
            Scanner in = new Scanner(System.in);
            if(in.hasNextInt()){
                sel=in.nextInt();
                test.testStart(sel);
            }
            else{
                System.out.println("Ok..... what?");
            }
            
            
            }
            
                    

	}

}
