import java.util.List;
public class OwnerUtil extends NormalUtil{
    static boolean ownerlogin;

    public static void ownerMainMenu(List<ProductMenu> productMenus, Transaction transaction){
        OwnerUtil.ownerlogin = true;
        Owner owner = new Owner();
        int selection;
        do{

            selection = displayMenu("choose a function you want to use",
                    new String[]{
                            "Add User",
                            "Remove User",
                            "use seller functions",
                            "use cashier functions",
                            "obtain <cancelled transaction summary>",
                            "obtain <usernames_summary>",
                            "Exit",
                    },
                    "Please enter a selection");

            switch (selection){

                case 1:
//                    add User
                    System.out.println("Please enter information of the user you want to add");
                    String username = getString("username:");
                    String password = getString("password:");
                    String userType = getString("Choose and input a kind of user type (customer, cashier, seller, owner)");
                    while (!(userType.equals("customer") || userType.equals("cashier") || userType.equals("seller") || userType.equals("owner"))){
                        System.out.println("Wrong Input");
                        userType = getString("Choose and input a kind of user type (customer, cashier, seller, owner)");
                    }
                    owner.addUser(username,password,userType);
                    ownerMainMenu(productMenus,transaction);
                    break;

                case 2:
//                    Remove User
                    String delete_username = getString("Please enter the username you want to remove");
                    owner.removeUser(delete_username);
                    ownerMainMenu(productMenus,transaction);
                    break;

                case 3:
//                    go to seller mode
                    SellerMenu.display(productMenus,transaction);
                    break;

                case 4:
//                    go to cashier mode
                    CashierMenu.display(productMenus,transaction);
                    break;

                case 5:
                    try{
                        Owner.get_cancelled_report();
                    }
                    catch(Exception e){
                        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                        System.exit(0);
                    }
                    ownerMainMenu(productMenus,transaction);
                    break;

                case 6:
                    try{
                        Owner.get_user_list();
                    }
                    catch(Exception e){
                        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                        System.exit(0);
                    }
                    ownerMainMenu(productMenus,transaction);
                    break;

                case 7:
//                    exit
                    OwnerUtil.ownerlogin = false;
                    App.homepage(productMenus,transaction);
                    break;

            }
        }
        while (selection == -1);
    }
}
