import java.util.List;

public class ProductMenu extends NormalUtil{
    private String category;
    private List<Product> products;
    public ProductMenu(String category, List<Product> products){
        super();
        this.category = category;
        this.products = products;
    }

    public String getCategoryName(){
        return this.category;
    }

    public List<Product> getProducts(){
        return this.products;
    }

    public void display(List<ProductMenu> productMenus, Transaction transaction){
        int selection;
        do{
            for(int i=0;i<products.size();i++){
                System.out.println(products.get(i).toString());
            }
            selection = displayMenu("choose a function you want to use",
                    new String[]{
                            "Put product into the cart",
                            "Cancel transaction",
                            "Pay for transaction",
                            "Back",
                    },
                    "Please enter a selection");

            switch (selection){

                case 1:
//                    Put product into the cart
                    String pname = NormalUtil.getString("Input the name of the product(case insensitive):").toLowerCase();
                    Integer quantity = NormalUtil.getInt("Input the quantity of the product");
                    if (quantity <= 0){
                        System.out.println("Invaild quantity");
                        System.out.println("_________________"); // separate output
                        MainMenu.categoriesMenu(productMenus,transaction);
                        break;
                    }
                    boolean found = false;
//                    ---------------------------
//                    for(int i = 0; i < this.products.size(); i+=1){
//                        if (pname.equals(this.products.get(i).getName())){
//                            found = true;
//                            boolean success = this.products.get(i).updateInventory(quantity);
//                            if (!success) {
//                                System.out.println("Product out of stock ");
//                            }else{
//                                transaction.add_to_cart(new Product(this.products.get(i).getName(), this.products.get(i).getCategory(), this.products.get(i).getPrice(), quantity,0));
//                                //System.out.println(transaction.gettotalprice());
//                                //System.out.println(transaction.cart.get(0));
//                                //transaction.list_items();
//                                System.out.println("Product added");
//                            }
//                        }
//                    }
//                    ------------------------------ V2 BELOW
//                    get the current position
                    ProductMenu currentCategory = null;
                    for(int i=0;i<productMenus.size();i+=1){
                        if (this.products.equals(productMenus.get(i).getProducts())){
                            currentCategory = productMenus.get(i);
                        }
                    }

                    List<Product> products = currentCategory.getProducts();

                    for(int i = 0; i < products.size(); i+=1){
                        if (pname.equals(products.get(i).getName().toLowerCase())){
                            found = true;
                            boolean success = products.get(i).updateInventory(quantity);
                            if (!success) {
                                System.out.println("Product out of stock ");
                            }else{
                                transaction.add_to_cart(new Product(this.products.get(i).getName(), this.products.get(i).getItem_code(), this.products.get(i).getCategory(), this.products.get(i).getPrice(), quantity,0));
                                System.out.println("Product added");
                            }
                        }
                    }

//                    ----------------------

                    if (!found){
                        System.out.println("Product does not exist.");
                    }
                    System.out.println("_________________"); // separate output
                    MainMenu.categoriesMenu(productMenus,transaction);
                    break;

                case 2:
//                    Cancel transaction
                    transaction.cancelbyuser();
                    transaction = null;
                    App.homepage(productMenus,transaction);
                    break;

                case 3:
//                    pay for transaction
                    MainMenu.paymentInterface(productMenus,transaction);
                    break;

                case 4:
//                    back
                    MainMenu.categoriesMenu(productMenus,transaction);
                    break;
            }
        }
        while (selection == -1);
    }

    public void addProduct(Product product){
        products.add(product);
//        also need to add to the database
    }
}
