
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

import java.util.*;
import java.lang.String;
public class App extends NormalUtil{
    static ArrayList<String> helperlist;
    static String username=null;
    static String password=null;
    static String date;
    static String time;
    static Date d;
    static SimpleDateFormat sdf;
    static Connection c;
    static List<Product> items;
    static List<Product> drinkList;
    static List<Product> chocList;
    static List<Product> chipList;
    static List<Product> candyList;
    static ProductMenu drinks;
    static ProductMenu chocolates;
    static ProductMenu chips;
    static ProductMenu candies;
    static List<ProductMenu> test;
    static Transaction transaction = null;

    public static void helper(){
        d = new Date();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        date = sdf.format(d);
        sdf = new SimpleDateFormat("HH:mm:ss");
        time = sdf.format(d);

        c = sqliteFunction.connect();
        items = readItem(c);
        try{
            c.close();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        drinkList = new ArrayList<>();
        chocList = new ArrayList<>();
        chipList = new ArrayList<>();
        candyList= new ArrayList<>();

        for(int i=0;i<items.size();i++){
            String category = items.get(i).getCategory();
            if (category.equals("Drinks")){
                drinkList.add(items.get(i));
            }
            else if(category.equals("Chocolates")){
                chocList.add(items.get(i));
            }
            else if(category.equals("Chips")){
                chipList.add(items.get(i));
            }
            else if(category.equals("Candies")){
                candyList.add(items.get(i));
            }
            else{
                System.out.println("can't find the category");
            }
        }

        drinks = new ProductMenu("Drinks",drinkList);
        chocolates = new ProductMenu("Chocolates",chocList);
        chips = new ProductMenu("Chips",chipList);
        candies = new ProductMenu("Candies",candyList);

        test = new ArrayList<>(Arrays.asList(drinks,chocolates,chips,candies));

    }
    public static void main(String[] args) {

        helper();
        homepage(test,App.transaction);
    }
    public static void homepage(List<ProductMenu> productMenus, Transaction transaction){

        App.d = new Date();
        App.sdf = new SimpleDateFormat("yyyy-MM-dd");
        App.date = App.sdf.format(App.d);
        App.sdf = new SimpleDateFormat("HH:mm:ss");
        App.time = App.sdf.format(App.d);

        c = sqliteFunction.connect();
        items = readItem(c);
        try{
            c.close();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        drinkList.clear();
        chocList.clear();
        chipList.clear();
        candyList.clear();


        for(int i=0;i<items.size();i++){
            String category = items.get(i).getCategory();
            if (category.equals("Drinks")){
                drinkList.add(items.get(i));
            }
            else if(category.equals("Chocolates")){
                chocList.add(items.get(i));
            }
            else if(category.equals("Chips")){
                chipList.add(items.get(i));
            }
            else if(category.equals("Candies")){
                candyList.add(items.get(i));
            }
            else{
                System.out.println("can't find the category");
            }
        }

        drinks = new ProductMenu("Drinks",drinkList);
        chocolates = new ProductMenu("Chocolates",chocList);
        chips = new ProductMenu("Chips",chipList);
        candies = new ProductMenu("Candies",candyList);

        productMenus = new ArrayList<>(Arrays.asList(drinks,chocolates,chips,candies));


        int selection;
        do{
            selection=displayMenu("make a selection",
                new String[]{
                    "create an customer account",
                    "login",
                    "Anonymous customer",
                    "exit"
                },
                "please enter a selection");
            
            switch(selection){
                case 1 :
                    username=getString("please input a name to sign up.");
                    System.out.println(username);
                    password=getString("please input a password to sign up.");
                    boolean Signup_success=Sign_up.check(username,password);
                    if(Signup_success==false){
                        username=null;
                        password=null;
                        homepage(productMenus,transaction);
                    }else {
                        App.transaction = new Transaction(date,time,username);
                        MainMenu.displayMainMenu(productMenus, App.transaction);
                    }
                    break;
                case 2:
                    username=getString("please input a name to login.");
                    password=getString("please input a password to login.");
                    int Login_success=Login.check(username,password);
                    if(Login_success==0){
                        username=null;
                        password=null;
                        homepage(productMenus,transaction);
                    }else if(Login_success==1) {
                        App.transaction = new Transaction(date, time, username);
                        MainMenu.displayMainMenu(productMenus, App.transaction);
                    }
                    else if(Login_success==2){
                        SellerMenu.display(productMenus,transaction);
                    }
                    else if(Login_success==3){
                        CashierMenu.display(productMenus,transaction);
                    }
                    else if(Login_success==4){
                        OwnerUtil.ownerMainMenu(productMenus,transaction);
                    }
                    break;
                case 3:
                    username = "anonymous";
                    password = "anonymous";
                    App.transaction = new Transaction(date,time,username);
                    MainMenu.displayMainMenu(productMenus, App.transaction);
                    break;
                case 4:
//                    System.exit(0);
                    break;
            }
        }
        while(selection == -1);

    }

    public static List<Product> readItem(Connection c){
        List<Product> items = new ArrayList<>();
        try {
            String query = "SELECT * FROM Item;";
            ResultSet rs = sqliteFunction.select(query,c);
            while (rs.next()) {
                String name = rs.getString("name");
                String item_code = rs.getString("item_code");
                String category = rs.getString("category");
                double price = rs.getDouble("price");
                int inventory = rs.getInt("quantity_remain");
                int total_sold = rs.getInt("quantity_total_sold");
                Product p = new Product(name,item_code,category,price,inventory,total_sold);
                items.add(p);
            }
            return items;
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return items;
    }

}