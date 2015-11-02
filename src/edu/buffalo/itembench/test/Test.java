/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.buffalo.itembench.test;

import edu.buffalo.itembench.db.DbConnector;
import edu.buffalo.itembench.db.DbException;
import java.sql.Connection;
import java.util.Scanner;

/**
 *
 * @author Warren
 */
public class Test {
    DbConnector dbcon;
    
    int wrkld=0; //Holds value for user choice for workload
    
    /*
        Metrics array used to not what metrics user wishes to calculate
        1. throughput
        2. runtime
        3. latency
        4. heap size
    */
    int metrics[]={0,0,0,0};
    
    public void testStart(int sel) {

        switch (sel) {
            case 1:
                dbcon = new DbConnector("sqldriver", "sqlurl", "sqldbName");
                break;
            case 2:
                dbcon = new DbConnector("berdriver", "berurl", "berdbName");
                break;
            case 3:
                dbcon = new DbConnector("apadriver", "apaurl", "apadbName");
                break;
            case 4:
                dbcon = new DbConnector("h2driver", "h2url", "h2dbName");
                break;
            case 5:
                dbcon = new DbConnector("hsqldriver", "hsqlurl", "hsqldbName");
                break;
            case 6:
                System.out.println("Exiting. Bye bye!!");
                return;
                
            default:
                System.out.println("Please enter one of the options. Please?");
                return;
            
        }
        
        
        
        //Open connection based on database
        try {
            Connection connection=dbcon.getConnection();
        } catch (DbException e) {
            System.out.println(e);
        }
        

        //Get user input for metrics
        getMetrics();
        

        //Get the workload the user wishes to run
        //TO DO: will be changed. Place holder for now
        while(wrkld!=3){
            System.out.println("Please enter the number for workload you want to run");
            System.out.println("1. Workload A - 100% insert");
            System.out.println("2. Workload B - 100% read");
            System.out.println("3. Restart");

            Scanner in = new Scanner(System.in);
            if(in.hasNextInt()){
                wrkld=in.nextInt();
                switch(wrkld){
                    case 1:
                        //Call init method for workload A
                        //Method (metrics,connection)
                        break;
                    case 2:
                        //Call init method for workload A
                        //Method (metrics,connection)
                        break;
                    case 3:
                        System.out.println("Restarting!");
                        //reset the metrics vector
                        for(int i=0;i<metrics.length;i++){
                            metrics[i]=0;
                        }
                        
                        //Close connection and return
                        try {
                            dbcon.closeConnection();
                        } catch (DbException e) {
                            System.out.println(e);
                        }

                        return;
                    default:
                        System.out.println("Please enter one of the options");
                }
                
            }
            else{
                System.out.println("Ok..... what?");
            }
        }
        
        //Close connection after workload is run
        try {
            dbcon.closeConnection();
        } catch (DbException e) {
            System.out.println(e);
        }


    }

    public void getMetrics() {
        int opt=0;
        System.out.println("Please enter the number for metrics you want to calculate");
        System.out.println("1. Throughput");
        System.out.println("2. Runtime");
        System.out.println("3. Latency");
        System.out.println("4. Heap size");
        System.out.println("5. Done");
            
        while(opt!=5){
            Scanner in = new Scanner(System.in);
            if(in.hasNextInt()){
                opt=in.nextInt();
                switch(opt){
                    case 1:
                        metrics[0]=1;
                        break;
                    case 2:
                        metrics[1]=1;
                        break;
                    case 3:
                        metrics[2]=1;
                        break;
                    case 4:
                        metrics[3]=1;
                        break;
                    case 5:
                        if(metrics[0]==0&&metrics[1]==0&&metrics[2]==0&&metrics[3]==0){
                            System.out.println("Please enter at least one metric");
                            opt=0;
                            break;
                        }
                        else{
                            System.out.println("Got the metrics!");
                            break;
                        }
                    
                    default:
                        System.out.println("Please enter one of the options");
                }
            }
            else{
                System.out.println("Ok..... what?");
            }
        
        }
    }
}
