public class Product {
    private String name;
    private String category;
    private double price;
    private int inventory;
    private int total_sold;
    private String item_code;
    public Product(String name, String item_code, String category, double price, int inventory, int total_sold){
        this.name = name;
        this.category = category;
        this.price = price;
        this.inventory = inventory;
        this.total_sold = total_sold;
        this.item_code = item_code;
    }

    public String getName(){
        return this.name;
    }

    public double getPrice(){
        return this.price;
    }

    public int getInventory(){ return this.inventory; }

    public String getCategory(){
        return this.category;
    }

    public String getItem_code(){
        return this.item_code;
    }

    public boolean updateInventory(int quantity){
        if (quantity > this.inventory || quantity <= 0){
            return false;
        }else {
            this.inventory = this.inventory - quantity;
            return true;
        }
    }

    public void addInventory(int inventory){
        this.inventory += inventory;
    }

    @Override
    public String toString(){
        return "Item_code: " + this.item_code + "  Product: "+ this.name + "  Inventory: " + this.inventory + "  Price: " + price;
    }

}
