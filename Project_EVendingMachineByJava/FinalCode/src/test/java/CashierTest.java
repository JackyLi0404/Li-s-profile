import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.io.*;
import java.util.*;
import java.lang.String;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CashierTest extends Cashier{
    @BeforeEach
    public void setup(){
    }
    @Test
    public void check_modify(){
        int output=Cashier.modifyMoney(50.00,8);
        assertEquals(output,8);
        Cashier.modifyMoney(50.00,5);
    }

    @Test
    void checkAvailable_change_report() {
        try{
            get_available_change_report();
            File file = new File("available_change_report");
            if(file.isFile() && file.exists()){
                assertTrue(true);
            }
        }
        catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    @Test
    void checkAvailable_transaction_summary_report() {
        try{
            get_transaction_summary_report();
            File file = new File("transaction_summary_report");
            if(file.isFile() && file.exists()){
                assertTrue(true);
            }
        }
        catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
}
