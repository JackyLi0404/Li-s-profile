import java.sql.Connection;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainMenu extends NormalUtil{
    public MainMenu(){
        super();
    }

    public static void displayMainMenu(List<ProductMenu> productMenus, Transaction transaction){
        int selection;
        do{
            list_5_items();


            selection = displayMenu("choose a function you want to use",
                    new String[]{
                            "Shopping cart",
                            "List all categories",
                            "Cancel transaction",
                            "Pay for transaction",
                    },
                    "Please enter a selection");

            switch (selection){
                case 0:
                    App.homepage(App.test,App.transaction);
                    break;

                case 1:
//                    go shopping cart
                    transaction.list_items();
                    System.out.println("_________________"); // separate output
                    displayMainMenu(productMenus,transaction);
                    break;

                case 2:
//                    list all categoriesMenu
                    categoriesMenu(productMenus, transaction);
                    break;

                case 3:
//                    cancel transaction
                    transaction.cancelbyuser();
                    transaction = null;
                    App.homepage(productMenus,transaction);
                    break;

                case 4:
//                    pay for transaction
                    paymentInterface(productMenus, transaction);
                    break;

            }
        }
        while (selection == -1);
    }


    public static void categoriesMenu(List<ProductMenu> productMenus, Transaction transaction){
        int selection;
        do{
            selection = displayMenu("choose a function you want to use",
                    new String[]{
                            "Drinks",
                            "Chocolates",
                            "Chips",
                            "Candies",
                            "Cancel transaction",
                            "Pay for transaction",
                            "Back",
                    },
                    "Please enter a selection");

            switch (selection){

                case 1:
//                    drink
                    productMenus.get(0).display(productMenus, transaction);
                    break;

                case 2:
//                    choc
                    productMenus.get(1).display(productMenus, transaction);
                    break;

                case 3:
//                    chips
                    productMenus.get(2).display(productMenus, transaction);
                    break;

                case 4:
//                    candies
                    productMenus.get(3).display(productMenus, transaction);
                    break;

                case 5:
//                    cancel transaction
                    transaction.cancelbyuser();
                    transaction = null;
                    App.homepage(productMenus,transaction);
                    break;

                case 6:
//                    pay for transaction
                    paymentInterface(productMenus, transaction);
                    break;

                case 7:
//                    back
                    displayMainMenu(productMenus, transaction);
                    break;

            }
        }
        while (selection == -1);
    }



    public static void paymentInterface(List<ProductMenu> productMenus, Transaction transaction){
        int selection;
        do{ transaction.list_items();
            selection = displayMenu("choose a function you want to use",
                    new String[]{
                            "Pay by cash",
                            "Pay by card",
                            "Cancel transaction",
                            "Back",
                    },
                    "Please enter a selection");

            switch (selection){

                case 1:
//                    pay by cash
                    break;

                case 2:
                    CreditCardMenu.creditcard(productMenus, transaction);
                    break;

                case 3:
//                    cancel transaction
                    transaction.cancelbyuser();
                    transaction = null;
                    App.homepage(productMenus,transaction);
                    break;

                case 4:
//                    back to main menu
                    displayMainMenu(productMenus, transaction);
                    break;
            }
        }
        while (selection == -1);
    }

    public static void list_5_items(){
        try {
            Connection c = sqliteFunction.connect();
            String query = "SELECT user_last_5_items FROM User WHERE user_name ='"+ App.username + "';";
            ResultSet rs = sqliteFunction.select(query,c);
            String last_5 = "nothing";
            while (rs.next()) {
                last_5 = rs.getString("user_last_5_items");
            }
            System.out.println("last 5 items: " + last_5);
            c.close();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

}
