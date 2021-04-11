import java.util.*;
import java.lang.*;

public class CreditCardMenu extends NormalUtil{
    public CreditCardMenu(){ super(); }
    static String CardHolder = null;
    static String CardNumber = null;
    public static void creditcard(List<ProductMenu> productMenus, Transaction transaction){
        int selection;
        do{
            selection=NormalUtil.displayMenu("make a selection",
                    new String[]{
                        "provide your card details",
                        "cancel the transaction",
                    },
                    "please enter a selection");

            switch(selection){
                case 1 :
                    boolean card_saved = Credit_card.checksaved(App.username);
                    boolean creditcard_valid = false;
                    if(card_saved == false){
                        CardHolder= getString("please provide cardholder's name: ");
                        CardNumber= getString("please provide your card number: ");
                        creditcard_valid = Credit_card.checkvalid(App.username, CardHolder,CardNumber);
                        if(creditcard_valid== false) {
                            CardHolder = null;
                            CardNumber = null;
                            CreditCardMenu.creditcard(productMenus, transaction);
                        }
                    }else{
                        creditcard_valid = true;
                    }
                    // save the transaction data and go back to homepage
                    if (creditcard_valid){
                        transaction.setpaymentmethod("card");
                        transaction.success();
                        transaction = null;
                        App.transaction = null;
                        App.homepage(productMenus,App.transaction);}
                    break;

                case 2:
                    // cancel the transaction
                    transaction.cancelbyuser();
                    transaction = null;
                    App.homepage(productMenus,transaction);
                    break;
            }
        }
        while(selection == -1);
    }
}