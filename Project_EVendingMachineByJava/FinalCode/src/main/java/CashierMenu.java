
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

public class CashierMenu extends NormalUtil{
    public CashierMenu(){

    }
    public static void display(List<ProductMenu> productMenus, Transaction transaction){
        int selection;
        do{
            selection = displayMenu("choose a function you want to use",
                    new String[]{
                            "change the quantity 100 dollars",
                            "change the quantity 50 dollars",
                            "change the quantity 20 dollars",
                            "change the quantity 10 dollars",
                            "change the quantity 5 dollars",
                            "change the quantity 2 dollars",
                            "change the quantity 1 dollar",
                            "change the quantity 0.5 dollars",
                            "change the quantity 0.2 dollars",
                            "change the quantity 0.1 dollars",
                            "change the quantity 0.05 dollars",
                            "obtain <available change report>",
                            "obtain <transaction summary report>",
                            "exit",
                            //"helper"
                    },
                    "Please enter a selection");

            switch(selection){
                case 1:
                    int quantity_100=getInt("Please enter the new quantity of 100 dollars");
                    Cashier.modifyMoney(100.00,quantity_100);
                    CashierMenu.display(productMenus,transaction);
                    break;
                case 2:
                   int quantity_50=getInt("Please enter the new quantity of 50 dollars");
                    Cashier.modifyMoney(50.00,quantity_50);
                    CashierMenu.display(productMenus,transaction);
                    break;
                case 3:
                    int quantity_20=getInt("Please enter the new quantity of 20 dollars");
                    Cashier.modifyMoney(20.00,quantity_20);
                    CashierMenu.display(productMenus,transaction);
                    break;
                case 4:
                    int quantity_10=getInt("Please enter the new quantity of 10 dollars");
                    Cashier.modifyMoney(10.00,quantity_10);
                    CashierMenu.display(productMenus,transaction);
                    break;
                case 5:
                    int quantity_5=getInt("Please enter the new quantity of 5 dollars");
                    Cashier.modifyMoney(5.00,quantity_5);
                    CashierMenu.display(productMenus,transaction);
                    break;
                case 6:
                    int quantity_2d=getInt("Please enter the new quantity of 2 dollars");
                    Cashier.modifyMoney(2.00,quantity_2d);
                    CashierMenu.display(productMenus,transaction);
                    break;
                case 7:
                    int quantity_1=getInt("Please enter the new quantity of 1 dollar");
                    Cashier.modifyMoney(1.00,quantity_1);
                    CashierMenu.display(productMenus,transaction);
                    break;
                case 8:
                    int quantity_05=getInt("Please enter the new quantity of 0.5 dollar");
                    Cashier.modifyMoney(0.50,quantity_05);
                    CashierMenu.display(productMenus,transaction);
                    break;
                case 9:
                    int quantity_20c=getInt("Please enter the new quantity of 0.2 dollar");
                    Cashier.modifyMoney(0.20,quantity_20c);
                    CashierMenu.display(productMenus,transaction);
                    break;
                case 10:
                    int quantity_01=getInt("Please enter the new quantity of 0.1 dollar");
                    Cashier.modifyMoney(0.10,quantity_01);
                    CashierMenu.display(productMenus,transaction);
                    break;
                case 11:
                    int quantity_005=getInt("Please enter the new quantity of 0.05 dollar");
                    Cashier.modifyMoney(0.05,quantity_005);
                    CashierMenu.display(productMenus,transaction);
                    break;

                case 12:
                    try{
                        Cashier.get_available_change_report();
                    }
                    catch(Exception e){
                        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                        System.exit(0);
                    }
                    CashierMenu.display(productMenus,transaction);
                    break;

                case 13:
                    try{
                        Cashier.get_transaction_summary_report();
                    }
                    catch(Exception e){
                        System.out.println("Something wrong here in UI!");
                        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                        System.exit(0);
                    }
                    CashierMenu.display(productMenus,transaction);
                    break;

                case 14:
                    if(OwnerUtil.ownerlogin){
                        OwnerUtil.ownerMainMenu(productMenus,transaction);
                    }else {
                        App.homepage(productMenus, transaction);
                    }
                    break;

                /*
                case 15:
                    Cashier.helper();
                    CashierMenu.display(productMenus,transaction);
                    break;

                */
            }
        }
        while(selection==-1);
    }
}

