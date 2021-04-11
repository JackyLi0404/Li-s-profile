import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class Transaction{

    private List<Product> cart;
    private double total_price;
    private final String date;
    private final String time;
    private double changes;
    private final String username;
    private String payment_method; // true for cash, false for card
    private String canceled_reason;

    Transaction(String date, String time, String username){
        this.date = date;
        this.time = time;
        this.username = username;
        this.cart = new ArrayList<>();
        this.total_price = 0;
        this.changes = 0;
    }

    // adjust according to item class !!!!!!!!!!!!!!!!!!!!!
    public List<String> to_string() {
        List<String> ls = new ArrayList<>();
        for (int i = 0;i < cart.size(); i+=1 ){
            ls.add(cart.get(i).getName() + "," + cart.get(i).getInventory());
        }
        return ls;
    }

    public void list_items(){
        System.out.println("Shopping cart:");
        List<String> ls = this.to_string();
        if (ls.size() == 0){
            System.out.println("empty");
        }else {
            System.out.println("product name,quantity");
            ls = this.to_string();
            for (int i = 0; i < ls.size(); i += 1) {
                System.out.println(ls.get(i));
            }
        }
        System.out.println("total price: " + this.gettotalprice());

    }

    public void add_to_cart(Product item){
        boolean exist = false;
        if (this.cart.size()!= 0){
            for (int i = 0;i < this.cart.size(); i+=1 ) {
                if (this.cart.get(i).getName().equals(item.getName()) && this.cart.get(i).getPrice() == item.getPrice()) {
                    this.cart.get(i).addInventory(item.getInventory());
                    exist = true;
                    break;
                }
            }
        }
        if (!exist){
            this.cart.add(item);
        }
        DecimalFormat df = new DecimalFormat("0.00");
        this.total_price += Double.parseDouble(df.format(item.getInventory()*item.getPrice()));
    }

    public double gettotalprice(){ return this.total_price ; }

    public String getDate() { return this.date; }

    public String getTime() { return this.time; }

    public String getUsername() { return this.username; }

    public double getchanges() { return this.changes; }

    public void setchanges(double changes) { this.changes = changes; }

    public void setpaymentmethod(String method) {
        if( method.equals("cash") || method.equals("card")) {
            this.payment_method = method;
        }else{
            System.out.println("error payment method");
        }
    }

    public String getpaymentmethod(){ return this.payment_method; }

    public void setcanceledreason(String reason){ this.canceled_reason = reason; }

    public String getcanceledreason(){ return this.canceled_reason; }

    public void cancelbyuser(){
        this.setcanceledreason("user cancelled");
        this.cancel();
    }

    public void cancelbytimeout(){
        this.setcanceledreason("timeout");
        this.cancel();
    }

    public void cancelbynochange() {
        this.setcanceledreason("change not available");
        this.cancel();
    }

    public void cancel() {
        String query = "INSERT into cancelledTransaction(cancelled_transaction_date,cancelled_transaction_time,user_name,cancelled_transaction_reason) values('"+this.date
                +"','"+this.time+"','"+this.username +"','"+ this.canceled_reason+ "');";
        Connection c=sqliteFunction.connect();
        sqliteFunction.insert(query,c);
        //System.out.println("added");
        String select = "select * from cancelledTransaction;";
        ResultSet rs = sqliteFunction.select(select,c);
        /*
        try {
            while (rs.next()) {
                String a = rs.getString("cancelled_transaction_date");
                System.out.println(a);
                String b = rs.getString("cancelled_transaction_time");
                System.out.println(b);
                String i = rs.getString("user_name");
                System.out.println(i);
                String d = rs.getString("cancelled_transaction_reason");
                System.out.println(d);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        */
 //for presenting
        try{
            c.close();
        }
        catch(Exception e){
            System.exit(0);
        }
    }

    public void success(){
        List<String> ls = this.to_string();
        String items = String.join(",",ls);
        String query = "INSERT into Transactions(transaction_date,transaction_time,transaction_amount,transaction_items,transaction_change,transaction_method,user_name) values('"+this.date
                +"','"+this.time+"',"+this.total_price+",'"+items+"',"+this.changes+",'"+this.payment_method+"','"+this.username + "');";
        Connection c=sqliteFunction.connect();
        sqliteFunction.insert(query,c);

        //System.out.println("added");
        /*
        String select = "select * from Transactions;";
        ResultSet rs = sqliteFunction.select(select,c);
        try {
            while (rs.next()) {
                String a = rs.getString("transaction_date");
                System.out.println(a);
                String b = rs.getString("transaction_time");
                System.out.println(b);
                String i = rs.getString("transaction_amount");
                System.out.println(i);
                String d = rs.getString("transaction_items");
                System.out.println(d);
                String g = rs.getString("transaction_change");
                System.out.println(g);
                String f = rs.getString("transaction_method");
                System.out.println(f);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        */
        //for presenting

        String findfq = "select user_last_5_items from User where user_name = '"+ this.username +"';";
        ResultSet output=sqliteFunction.select(findfq,c);
        String lastf = null;
        try {
            while (output.next()) {
                lastf = output.getString("user_last_5_items");
                //System.out.println(lastf);
            }
        } catch(Exception e){
            System.out.println(e);
        }
        List<String> fitem = null;
        if (lastf == null){
            fitem = new ArrayList<>();
        }else {
            fitem = new ArrayList<String>(Arrays.asList(lastf.split(",")));
        }
        for (int i = 0; i < this.cart.size();i++){
            String itemq = "update Item set quantity_remain = quantity_remain - " + this.cart.get(i).getInventory() + ", quantity_total_sold = quantity_total_sold + "+ this.cart.get(i).getInventory() +" where name = '" + this.cart.get(i).getName() +"';";
            sqliteFunction.update(itemq,c);
            if (!fitem.contains(this.cart.get(i).getName())) {
                if (fitem.size() == 5) {
                    fitem.remove(0);
                }
                fitem.add(this.cart.get(i).getName());
            }
        }
        String five = String.join(",",fitem);
        /*
        try {
            String qs = "SELECT * FROM Item where name = 'Sprite';";
            ResultSet rs = sqliteFunction.select(qs,c);
            while (rs.next()) {
                int inventory = rs.getInt("quantity_remain");
                System.out.println(inventory);
                int total_sold = rs.getInt("quantity_total_sold");
                System.out.println(total_sold);
            }
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        */
        String upfiveq = "update User set user_last_5_items = '" + five + "' WHERE user_name = '" + this.username + "';";
        sqliteFunction.update(upfiveq,c);

        /*
        try {
            String qs = "SELECT * FROM User where user_name = 'right';";
            ResultSet rs = sqliteFunction.select(qs,c);
            while (rs.next()) {
                String i = rs.getString("user_last_5_items");
                System.out.println(i);
            }
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        */

        try{
            c.close();
        }
        catch(Exception e){
            System.exit(0);
        }
    }
}