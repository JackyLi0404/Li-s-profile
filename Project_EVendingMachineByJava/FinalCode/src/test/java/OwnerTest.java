import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OwnerTest {

    Owner o;

    @BeforeEach
    public void setup(){
        o = new Owner();
    }

    @Test
    public void getallusernameTest(){
        List<String> s = o.getAllUserNames();
        List<String> x = new ArrayList<>();
        x.add("admin");
        x.add("anonymous");
        x.add("cashier");
        x.add("left");
        x.add("right");
        x.add("seller");
        assertEquals(s,x);
    }

    @Test
    public void userlistTest(){
        try {
            Owner.get_user_list();
            File file = new File("usernames_summary.txt");
            Scanner read = new Scanner(file);
            List<String> s = new ArrayList<>();
            while(read.hasNextLine()){
                s.add(read.nextLine());
            }
            read.close();
            List<String> x = new ArrayList<>();
            x.add("Username: right, Role: customer ");
            x.add("Username: left, Role: customer ");
            x.add("Username: admin, Role: owner ");
            x.add("Username: seller, Role: seller ");
            x.add("Username: cashier, Role: cashier ");
            assertEquals(s,x);
        }catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Test
    public void cancelsummaryTest(){
        try {
            Connection c=sqliteFunction.connect();
            String delete = "delete from CancelledTransaction;";
            sqliteFunction.update(delete,c);
            Transaction t = new Transaction("2020-11-18","15:00:00","right");
            t.cancelbyuser();
            t = new Transaction("2020-11-18","16:00:00","left");
            t.cancelbytimeout();
            Owner.get_cancelled_report();
            File file = new File("cancelled_transaction_summary.txt");
            Scanner read = new Scanner(file);
            List<String> s = new ArrayList<>();
            while(read.hasNextLine()){
                s.add(read.nextLine());
            }
            read.close();
            List<String> x = new ArrayList<>();
            x.add("Username: right, Cancelled date: 2020-11-18, Cancelled time: 15:00:00, Cancelled reason: user cancelled ");
            x.add("Username: left, Cancelled date: 2020-11-18, Cancelled time: 16:00:00, Cancelled reason: timeout ");
            sqliteFunction.update(delete,c);
            try{
                c.close();
            }
            catch(Exception e){
                System.exit(0);
            }
            assertEquals(x,s);
        }catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Test
    public void adduserTest(){
        o.addUser("up","up","customer");
        o.addUser("down","down","seller");
        try {
            Owner.get_user_list();
            File file = new File("usernames_summary.txt");
            Scanner read = new Scanner(file);
            List<String> s = new ArrayList<>();
            while(read.hasNextLine()){
                s.add(read.nextLine());
            }
            read.close();
            List<String> x = new ArrayList<>();
            x.add("Username: right, Role: customer ");
            x.add("Username: left, Role: customer ");
            x.add("Username: admin, Role: owner ");
            x.add("Username: seller, Role: seller ");
            x.add("Username: cashier, Role: cashier ");
            x.add("Username: up, Role: customer ");
            x.add("Username: down, Role: seller ");
            Connection c=sqliteFunction.connect();
            String delete = "delete from User where user_name = 'up' or user_name = 'down';";
            sqliteFunction.update(delete,c);
            try{
                c.close();
            }
            catch(Exception e){
                System.exit(0);
            }
            assertEquals(x,s);
        }catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    @Test
    public void removeuserTest(){
        o.removeUser("seller");
        o.removeUser("cashier");
        try {
            Owner.get_user_list();
            File file = new File("usernames_summary.txt");
            Scanner read = new Scanner(file);
            List<String> s = new ArrayList<>();
            while(read.hasNextLine()){
                s.add(read.nextLine());
            }
            read.close();
            List<String> x = new ArrayList<>();
            x.add("Username: right, Role: customer ");
            x.add("Username: left, Role: customer ");
            x.add("Username: admin, Role: owner ");
            Connection c=sqliteFunction.connect();
            o.addUser("seller", "seller", "seller");
            o.addUser("cashier", "cashier", "cashier");
            assertEquals(x,s);
        }catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}