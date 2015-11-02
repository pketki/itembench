/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.buffalo.itembench.test;

import edu.buffalo.itembench.db.DbConnector;
import edu.buffalo.itembench.db.DbException;
import java.util.Scanner;

/**
 *
 * @author Warren
 */
public class Test {
    DbConnector dbcon;
    
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
            dbcon.getConnection();
        } catch (DbException e) {
            System.out.println(e);
        }

        //Close connection
        try {
            dbcon.closeConnection();
        } catch (DbException e) {
            System.out.println(e);
        }


    }
}
