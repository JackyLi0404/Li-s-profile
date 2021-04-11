

import java.util.*;
import java.lang.String;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
public class Login{
    public static int check(String username, String password){
        int type=0;
        boolean success=true;
        String sql="select * from User where user_name='"+username+"' and user_password='"+password+"';";
        Connection c=sqliteFunction.connect();
        ResultSet output=sqliteFunction.select(sql,c);
      //interact with database
      try{
        if(output.next()==false){
          success=false;
        }
        else{
          String user_type=output.getString("user_type");//这里
          if(user_type.equals("customer")){
            type=1;//customer =1
          }
          else if(user_type.equals("seller")){
            type=2;//seller
          }
        }
      }
      catch(Exception e){
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      }
      try{
        c.close();
      }
      catch(Exception e){
        System.exit(0);
      }
      
      
      if(success==true){
        System.out.println("Hi "+ username + " nice to meet you :) ");
        // go to the transaction part
      }
      else{
        System.out.println("Sorry the login information is wrong");
        //go back to the homepage
      }
      if(success==false){
        return 0;
      }
      return type;
    }
}