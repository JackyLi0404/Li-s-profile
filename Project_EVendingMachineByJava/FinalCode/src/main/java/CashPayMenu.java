import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CashPayMenu extends NormalUtil {
    public CashPayMenu() {
        super();
    }

    public static void pay(List<ProductMenu> productMenus, Transaction transaction) {
        int selection;

        do {
            selection = NormalUtil.displayMenu("make a selection",
                    new String[]{
                            "start paying cash",
                            "cancel the transaction",
                            "back"
                    },
                    "please enter a selection");
            // cancel the transaction
            switch (selection) {
                case 1 -> {
                    if (transaction.gettotalprice() == 0) {
                        System.out.println("your cart is empty no need to pay!");
                        MainMenu.paymentInterface(productMenus, transaction);
                        break;
                    }
                    CashPay cashPay = new CashPay(CashConfig.toCents(transaction.gettotalprice()));
                    for (String faceName : cashPay.getFaceValueNames()) {
                        int quantity = NormalUtil.getInt("insert the quantity for face " + faceName + ":");
                        cashPay.setFaceQuantity(faceName, quantity);
                    }
                    if (!cashPay.getReturnEnough()) {
                        System.out.println("Need to pay: " + CashConfig.toDollars(cashPay.getPayPrice()) +
                                ", But Inserted total: " + CashConfig.toDollars(cashPay.getInsertedTotal()) + ".");
                        MainMenu.paymentInterface(productMenus, transaction);
                        break;
                    }
                    Connection connnection = sqliteFunction.connect();
                    CashSQL cashSQL = new CashSQL(connnection);
                    try {
                        // begin transaction
                        connnection.setAutoCommit(false);
                        transaction.list_items();
                        Map<String, Integer> databaseChanges = cashSQL.getDatabaseChanges();
                        Map<String, Integer> validChange = cashPay.getChange(databaseChanges);
                        if (validChange == null) {
                            System.out.println("not enough changes can be made! transaction failed");
                            // roll back if fail to pay
                            String choice = null;
                            while(true) {
                                choice = getString("Do you want to pay again? (enter y or n)").toLowerCase();
                                if (choice.equals("y") || choice.equals("n")){
                                    break;
                                }else{
                                    System.out.println("incorrect input");
                                }
                            }
                            if (choice.equals("y")){
                                connnection.rollback();
                                connnection.close();
                                MainMenu.paymentInterface(productMenus, transaction);
                                break;
                            }else if (choice.equals("n")){
                                connnection.rollback();
                                connnection.close();
                                transaction.cancelbynochange();
                                transaction = null;
                                App.homepage(productMenus,transaction);
                                break;
                            }

                        }
                        // write changes of database back
                        cashSQL.writeDatabaseChanges(cashPay.combineChanges(databaseChanges));
                        System.out.println();
                        System.out.println("Inserted cash total amount: " +
                                CashConfig.toDollars(cashPay.getInsertedTotal()));
                        System.out.println(cashPay.generateChangeString(validChange));
                        System.out.println("Thank you for the payment, have a good day");
                        String totalChanges = cashPay.getTotalChanges(validChange);
                        connnection.close();
                        //finish the transaction
                        transaction.setpaymentmethod("cash");
                        transaction.setchanges(Double.parseDouble(totalChanges));
                        transaction.success();
                        transaction = null;
                    } catch (SQLException throwables) {
                        try {
                            if (!connnection.isClosed()) {
                                // roll back if fail to pay when something error happend
                                connnection.rollback();
                                connnection.close();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        System.out.println("somthing wrong with payment");
                        throwables.printStackTrace();
                        break;
                    }
                    App.homepage(productMenus, transaction);
                }
                case 2 -> {
                    transaction.cancelbyuser();
                    App.homepage(productMenus, transaction);
                    break;
                }
                case 3 -> {
                    MainMenu.paymentInterface(productMenus, transaction);
                    break;
                }
            }
        }
        while (selection == -1);
    }
}