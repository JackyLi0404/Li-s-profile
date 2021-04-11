import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.*;
import java.lang.String;
import java.util.concurrent.TimeUnit;

public class Credit_card extends NormalUtil{
    public static boolean checkvalid(String UserName, String CardHolder, String CardNumber){
        boolean valid=false;
        String[] crcardname = null;
        String[] crcardnum = null;

        //read and compare the data from credit_cards.json
        try (FileReader r = new FileReader("credit_cards_name.txt");
             BufferedReader br = new BufferedReader(r)
        ) {
            String rline;
            while ((rline = br.readLine()) != null) {
                crcardname = rline.split(",");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileReader r = new FileReader("credit_cards_num.txt");
             BufferedReader br = new BufferedReader(r)
        ) {
            String rline;
            while ((rline = br.readLine()) != null) {
                crcardnum = rline.split(",");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0;i< crcardname.length;i++){
            if(CardHolder.equals(crcardname[i]) && CardNumber.equals(crcardnum[i])){
                valid = true;
            }
        }

        if (valid == true){
            System.out.println("Congratulation! The system would process with your provided card.");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Your transaction has been successfully processed!");

            if(!UserName.equals("anonymous")){
                System.out.println("Do you want to save your card details for next time shopping? y/n?");
                Scanner scanner_savecard=new Scanner(System.in);
                String temp_savecard = scanner_savecard.next();
                if(temp_savecard.equals("y")){
                    //save the card details in database
                    String sql= "update User" + " set card_holder_name = '" + CardHolder+ "',card_number = '"+CardNumber+"'"+" where user_name ='"+UserName+"';";


                    Connection c=sqliteFunction.connect();
                    sqliteFunction.insert(sql,c);
                    try{
                        c.close();
                    }
                    catch(Exception e){
                        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                        System.exit(0);
                    }

                    System.out.println("System has successfully saved your card!");
                }
            }
            System.out.println("Please take your items and welcome for your next shopping!");
        }
        else{
            System.out.println("Sorry, the card details do not match the credit card list.");
        }
        return valid;
    }

    public static boolean checksaved(String UserName){
        boolean saved = false;
        //interact with database with the given user name to get the creditcard attribute

        String sql="select card_holder_name, card_number "+" from User "+" where user_name='"+UserName+"';";
        Connection c = sqliteFunction.connect();
        ResultSet output=sqliteFunction.select(sql,c);
        String CardHolder = "null";
        String CardNumber = "null";
        try{
            while(output.next()){
                CardHolder = output.getString("card_holder_name");
                CardNumber = output.getString("card_number");
                if(CardHolder != null){
                    saved = true;
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

        if(saved == true){
            System.out.println("Congratulation! The system would process with your saved card.");
            System.out.println("Card Holder: " + CardHolder);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Your transaction has been successfully processed!");
            System.out.println("Please take your items and welcome for your next shopping!");
        }
        return saved;
    }
}
