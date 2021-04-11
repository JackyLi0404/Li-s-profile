
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

public  class Seller{

    public static void create_file(String filename) throws IOException {
        File file = new File(filename);

        if(file.isFile() && file.exists()){
            file.delete();
        }
        file.createNewFile();
        return;
    }

    public static void get_available_items_report() throws IOException{
       int remain_quantity = 0;
       String item_name = "null";
       String item_code = "null";
       String item_category = "null";
       Double item_price = 0.0;
       String item_details = "null";

       String filename = "available_items_report.txt";

       create_file(filename);

       File file = new File(filename);
       FileWriter fw = new FileWriter(file, true);
       BufferedWriter bw = new BufferedWriter(fw);

       String sql="select name, item_code, category, price, quantity_remain "+" from Item ";
       Connection c = sqliteFunction.connect();
       ResultSet output=sqliteFunction.select(sql,c);
       try{
           while(output.next()){
               remain_quantity = output.getInt("quantity_remain");
               if (remain_quantity != 0){
                   item_name = output.getString("name");
                   item_code = output.getString("item_code");
                   item_category = output.getString("category");
                   item_price = output.getDouble("price");
                   item_details = "item code: " + String.valueOf(item_code) + ", " + "item name: " + item_name + ", " + "category: " + item_category + ", " + "price: $" + String.valueOf(item_price) + ", " + "remain number: " + String.valueOf(remain_quantity) + " \n";
                   bw.write(item_details);
               }
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

    public static void get_summary_report() throws IOException{
        String item_details = "null";
        String item_code = "null";
        String item_name = "null";
        int item_sold = 0;

        String filename = "summary_report.txt";

        create_file(filename);

        File file = new File(filename);
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);

        String sql="select item_code, name, quantity_total_sold"+" from Item ";
        Connection c = sqliteFunction.connect();
        ResultSet output=sqliteFunction.select(sql,c);
        try{
            while(output.next()){
                item_name = output.getString("name");
                item_code = output.getString("item_code");
                item_sold = output.getInt("quantity_total_sold");
                item_details = "\"" + String.valueOf(item_code) + "; " + item_name + "; " + item_sold + "\",\n";
                bw.write(item_details);
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

   public static boolean changeQuantity(int number,String item_name){
     boolean success=true;
     if(number>15){
       System.out.println("Sorry Seller,the quantity of items can not be bigger than 15 :( ");
       success=false;
     }
     if(number<0){
       System.out.println("Sorry Seller,the quantity of items can not be smaller than 0 :( ");
       success=false;
     }
     else{
        Connection c=sqliteFunction.connect();
        String sql0="select * from Item where Item.name = '"+item_name+"';";
        ResultSet output=sqliteFunction.select(sql0,c);
        try{
          if(output.next()==false){
            System.out.println("Sorry seller, the item do not in the system");
            success=false;
          }
        }
        catch(Exception e){
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        
        if(success==true){
          String sql="update Item set quantity_remain = "+ number +" where name='"+item_name+"';";
          sqliteFunction.update(sql,c);
        }
        
        try{
            c.close();
          }
        catch(Exception e){
          System.exit(0);
        }
        return success; 
      } 
      return success; 
   }
   public static boolean changePrice(double price,String item_name){
      boolean success=true;
      Connection c=sqliteFunction.connect();
      String sql0="select * from Item where Item.name = '"+item_name+"';";
      ResultSet output=sqliteFunction.select(sql0,c);
      try{
        if(output.next()==false){
          System.out.println("Sorry seller, the item do not in the system");
          success=false;
        }
      }
      catch(Exception e){
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      } 
      if(price<0){
        success=false;
        System.out.println("The Price can not be negative");
      }
      if(success==true){
        String sql="update Item set price = "+ price +" where name='"+item_name+"';";
        sqliteFunction.update(sql,c);
      }
      
      try{
          c.close();
        }
      catch(Exception e){
        System.exit(0);
      }
      return success; 
      
   }
   public static boolean changeCategory(String category,String item_name){
     boolean success=true;
     boolean correct_category=false;
     System.out.println(category);
     if(category.equals("Candies")){
       correct_category=true;
     }
     else if(category.equals("Chips")){
       correct_category=true;
     }
     else if(category.equals("Chocolates")){
       correct_category=true;
     }
     else if(category.equals("Drinks")){
       correct_category=true;
     }
      Connection c=sqliteFunction.connect();
      String sql0="select * from Item where Item.name = '"+item_name+"';";
      ResultSet output=sqliteFunction.select(sql0,c);
      try{
        if(output.next()==false){
          System.out.println("Sorry seller, the item is not in the system");
          success=false;
        }
      }
      catch(Exception e){
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      }
      if(correct_category==false){
        System.out.println("Sorry seller, the category is not in the system");
        success=false;
      }
      if(success==true&&correct_category==true){
        String sql="update Item set category = '"+ category +"' where name='"+item_name+"';";
        sqliteFunction.update(sql,c);
      }
      
      try{
          c.close();
        }
      catch(Exception e){
        System.exit(0);
      }
      return success; 
      
   }
   public static boolean changeItemCode(String newItemCode,String item_name){
      boolean success=true;
      Connection c=sqliteFunction.connect();
      String sql0="select * from Item where Item.name = '"+item_name+"';";
      ResultSet output=sqliteFunction.select(sql0,c);
      try{
        if(output.next()==false){
          System.out.println("Sorry seller, the item do not in the system");
          success=false;
        }
      }
      catch(Exception e){
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      }

      String sql1="select * from Item where Item_code = '"+newItemCode+"';";
      ResultSet output2=sqliteFunction.select(sql1,c);
      try{
        if(output2.next()==true){
          System.out.println("Sorry seller, There is a same ItemCode in the System");
          success=false;
        }
      }
      catch(Exception e){
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      }
      
      if(success==true){
        String sql="update Item set item_code = '"+ newItemCode +"' where name='"+item_name+"';";
        sqliteFunction.update(sql,c);
      }
      
      try{
          c.close();
        }
      catch(Exception e){
        System.exit(0);
      }
      return success; 
   }
   public static boolean changeName(String newItemCode,String item_name){
      boolean success=true;
      Connection c=sqliteFunction.connect();
      String sql0="select * from Item where Item.item_code = '"+newItemCode+"';";
      ResultSet output=sqliteFunction.select(sql0,c);
      try{
        if(output.next()==false){
          System.out.println("Sorry seller, the item_code do not in the system");
          success=false;
        }
      }
      catch(Exception e){
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      }
      String sql1="select * from Item where Item.name = '"+item_name+"';";
      ResultSet output2=sqliteFunction.select(sql1,c);
      try{
        if(output2.next()==true){
          System.out.println("Sorry seller, There is a same Name in the system");
          success=false;
        }
      }
      catch(Exception e){
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      }
      if(success==true){
        String sql="update Item set name = '"+ item_name +"' where item_code ='"+newItemCode+"';";
        sqliteFunction.update(sql,c);
      }
      
      try{
          c.close();
        }
      catch(Exception e){
        System.exit(0);
      }
      return success; 
   }

}