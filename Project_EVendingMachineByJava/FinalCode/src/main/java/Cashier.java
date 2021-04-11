
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.lang.String;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
public class Cashier {
    public static int modifyMoney(double amount,int quantity){
        if(quantity<0){
            System.out.println("quantity should not be negative");
            return -1;
        }
        Connection c=sqliteFunction.connect();
        String sql="update Change set change_quantity ="+quantity+" where change_type="+amount+";";
        System.out.println(sql);
        sqliteFunction.update(sql,c);
        try{
            c.close();
        }
        catch(Exception e){
            System.exit(0);
        }
        return quantity;
    }

    public static void get_available_change_report() throws IOException {
        double type = 0.00;
        int quantity = 0;
        String change_details = "null";

        String filename = "available_change_report.txt";

        Seller.create_file(filename);

        File file = new File(filename);
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);

        String sql="select change_type, change_quantity "+" from Change";
        Connection c = sqliteFunction.connect();
        ResultSet output=sqliteFunction.select(sql,c);
        try{
            while(output.next()){
                type = output.getDouble("change_type");
                quantity = output.getInt("change_quantity");
                change_details = "Change type: " + String.valueOf(type) + ", " + "Quantity: " + String.valueOf(quantity) + " \n";
                bw.write(change_details);
            }
        }
        catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        try{
            c.close();
        }
        catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        bw.flush();
        bw.close();
        fw.close();
    }

    public static void get_transaction_summary_report() throws IOException {
        String date = "null";
        String time = "null";
        String item = "null";
        Double paid_amount = 0.00;
        Double returned_change = 0.00;
        String payment_method = "null";
        String transaction_details = "null";

        String filename = "transaction_summary_report.txt";

        Seller.create_file(filename);

        File file = new File(filename);
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);

        String sql="select transaction_date, transaction_time, transaction_items, transaction_amount, transaction_change, transaction_method" +" from Transactions";
        Connection c = sqliteFunction.connect();
        ResultSet output=sqliteFunction.select(sql,c);
        try{
            while(output.next()){
                date = output.getString("transaction_date");
                time = output.getString("transaction_time");
                item = output.getString("transaction_items");
                paid_amount = output.getDouble("transaction_amount");
                returned_change = output.getDouble("transaction_change");
                payment_method = output.getString("transaction_method");
                transaction_details = "Transaction date: " + date + ", Time: " + time + ", Trading items: " + item + ", Paid amount: " + String.valueOf(paid_amount) + ", Change amount: " + String.valueOf(returned_change) + ", Payment method: " + payment_method +" \n";
                bw.write(transaction_details);
            }
        }
        catch(Exception e) {
            System.out.println("Something wrong here in sql!");
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        try{
            c.close();
        }
        catch(Exception e){
            System.out.println("Something wrong here in closing sql!");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        bw.flush();
        bw.close();
        fw.close();
    }
}
