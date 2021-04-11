import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class Owner {


    public Owner(){
    }

    public void addUser(String username, String password, String userType){
        List<String> allUsernames = getAllUserNames();
        for(int i=0;i<allUsernames.size();i++){
            if (allUsernames.get(i).equals(username)){
                System.out.println("Username Already Exist");
                return;
            }
        }

        Connection c = sqliteFunction.connect();
        String sql = String.format("INSERT INTO User (user_name, user_password, user_type) VALUES ('%s', '%s', '%s');", username, password, userType);
        sqliteFunction.insert(sql,c);
        try{
            c.close();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Successfully added");
    }

    public void removeUser(String username){
        boolean userExist = false;
        if(username.equals("anonymous")){
            System.out.println("Can't remove anonymous account");
            return;
        }
        List<String> allUsernames = getAllUserNames();
        for(int i=0;i<allUsernames.size();i++){
            if (allUsernames.get(i).equals(username)){
                userExist = true;
            }
        }

        if(userExist == false){
            System.out.println("User doesn't exist");
            return;
        }

        Connection c = sqliteFunction.connect();
        String sql = String.format("DELETE FROM User WHERE user_name = '%s';",username);
        sqliteFunction.update(sql,c);

        try{
            c.close();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public List<String> getAllUserNames(){
        List<String> allUserNames = new ArrayList<>();
        Connection c = sqliteFunction.connect();
        String sql = "SELECT user_name FROM User;";

        try {
            ResultSet rs = sqliteFunction.select(sql,c);
            while (rs.next()) {
                String username = rs.getString("user_name");
                allUserNames.add(username);
            }
            rs.close();
            c.close();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return allUserNames;
    }

    public static void get_cancelled_report() throws IOException {
        String username = "";
        String date = "";
        String time = "";
        String reason = "";

        String filename = "cancelled_transaction_summary.txt";

        Seller.create_file(filename);

        File file = new File(filename);
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);

        String sql="select cancelled_transaction_date, cancelled_transaction_time, user_name, cancelled_transaction_reason FROM CancelledTransaction; ";
        Connection c = sqliteFunction.connect();
        ResultSet output=sqliteFunction.select(sql,c);
        try{
            while(output.next()){
                username = output.getString("user_name");
                date = output.getString("cancelled_transaction_date");
                time = output.getString("cancelled_transaction_time");
                reason = output.getString("cancelled_transaction_reason");
                String cancelledtransaction = "Username: " + username + ", Cancelled date: " + date + ", Cancelled time: " + time + ", Cancelled reason: " + reason + " \n";
                bw.write(cancelledtransaction);
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

    public static void get_user_list() throws IOException {
        String username = "";
        String role = "";

        String filename = "usernames_summary.txt";

        Seller.create_file(filename);

        File file = new File(filename);
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);

        String sql="select user_name,user_type FROM User; ";
        Connection c = sqliteFunction.connect();
        ResultSet output=sqliteFunction.select(sql,c);
        try{
            while(output.next()){
                username = output.getString("user_name");
                if (!username.equals("anonymous")) {
                    role = output.getString("user_type");
                    String cancelledtransaction = "Username: " + username + ", Role: " + role + " \n";
                    bw.write(cancelledtransaction);
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
}
