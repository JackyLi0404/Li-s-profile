import java.util.*;
public class SellerMenu extends NormalUtil{
    public SellerMenu(){

    }
    public static void display(List<ProductMenu> productMenus, Transaction transaction){
        int selection;
        do{
            selection = displayMenu("choose a function you want to use",
                    new String[]{
                            "change item name",
                            "change item code",
                            "change item category",
                            "change item quantity",
                            "change item price",
                            "obtain <available items report>",
                            "obtain <summary report>",
                            "Exit",
                    },
                    "Please enter a selection");
                
            switch(selection){
                case 1:
                    String item_newItemCode2=getString("please enter ItemCode for this items");
                    String item_name4=getString("please enter the new item_name of the item that you want to modify");
                    boolean success4=Seller.changeName(item_newItemCode2,item_name4);
                    SellerMenu.display(productMenus,transaction);
                    break;
                case 2:
                    String item_name3=getString("please enter the item_name of the item that you want to modify");
                    String item_newItemCode=getString("please enter new ItemCode for this items");
                    boolean success3=Seller.changeItemCode(item_newItemCode,item_name3);
                    SellerMenu.display(productMenus,transaction);
                    break;
                case 3:
                    String item_name2=getString("please enter the item_name of the item that you want to modify");
                    String newcategory=getString("please enter new category for this items");
                    boolean success2=Seller.changeCategory(newcategory,item_name2);
                    System.out.println(success2);
                    SellerMenu.display(productMenus,transaction);
                    break;
                case 4:
                    String item_name=getString("please enter the item_name of the item that you want to modify");
                    int quantity=getInt("please enter new quantity");
                    boolean success=Seller.changeQuantity(quantity,item_name);
                    SellerMenu.display(productMenus,transaction);
                    break;
                case 5:
                    String item_name1=getString("please enter the item_name of the item that you want to modify");
                    double price=getdouble("please enter new price");
                    boolean success1=Seller.changePrice(price,item_name1);
                    SellerMenu.display(productMenus,transaction);
                    break;
                case 6:
                    try{
                        Seller.get_available_items_report();
                    }
                    catch(Exception e){
                        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                        System.exit(0);
                    }
                    SellerMenu.display(productMenus,transaction);
                    break;

                case 7:
                    try{
                        Seller.get_summary_report();
                    }
                    catch(Exception e){
                        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                        System.exit(0);
                    }
                    SellerMenu.display(productMenus,transaction);
                    break;

                case 8:
                    App.homepage(productMenus,transaction);
                    break;
            }
        }
        while(selection==-1);
    }
}