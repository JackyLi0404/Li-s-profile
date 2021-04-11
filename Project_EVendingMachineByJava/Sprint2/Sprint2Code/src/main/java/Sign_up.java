
import java.util.*;
import java.lang.String;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class Sign_up{
    public static boolean check(String username, String password){
        boolean success=false;
      //interact with database
      String sql0="select * from User where user_name='"+username+"';";
      String sql="insert into User(user_name,user_password,card_holder_name,card_number,user_type,user_last_5_items) values('"+username
      +"','"+password+"',"+"null,null,'customer',null);";
      Connection c=sqliteFunction.connect();
      ResultSet output=sqliteFunction.select(sql0,c);
      try{
        if(output.next()==false){//no is right
          success=true;
        }
      }
      catch(Exception e){
        
      }
      if(success==true){
        sqliteFunction.insert(sql,c);
        try{
          c.close();
        }
        catch(Exception e){
          System.exit(0);
        }
        System.out.println("Hi "+ username + " nice to meet you :) ");
        // go to the transaction part
      }
      else{
        System.out.println("Sorry, there is a same name in the system, please enter another username to create an account");
        try {
          c.close();
        } catch (SQLException throwables) {
          throwables.printStackTrace();
        }
        //go back to the homepage
      }
      return success;
    }
}