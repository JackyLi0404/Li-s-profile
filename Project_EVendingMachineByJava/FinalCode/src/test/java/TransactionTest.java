import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.util.List;
import java.util.ArrayList;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    Transaction transaction;
    Product p;
    Product p2;
    Product p3;

    ByteArrayInputStream in;

    @BeforeEach
    public void setup(){
        transaction = new Transaction("2020-10-04","10:53","right");
        p = new Product("Sprite", "1002", "Drinks",1.00,10,0);
        p2 = new Product("Pepsi","1003", "Drinks",1.00,10,0);
        p3 = new Product("Sprite", "1004", "Drinks",1.00,1,0);
    }

    @Test
    void TestgetDate(){ assertEquals("2020-10-04",transaction.getDate()); }

    @Test
    void TestgetTime(){ assertEquals("10:53",transaction.getTime()); }

    @Test
    void TestgetId(){ assertEquals("right",transaction.getUsername()); }

    @Test
    void Testsetandgetchanges() {
        transaction.setchanges(1.00);
        assertEquals(transaction.getchanges(), 1.00);
    }

    @Test
    void Testsetpaymentbycard() {
        transaction.setpaymentmethod("card");
        assertEquals(transaction.getpaymentmethod(),"card");
    }

    @Test
    void Testsetpaymentbycash() {
        transaction.setpaymentmethod("cash");
        assertEquals(transaction.getpaymentmethod(),"cash");
    }

    @Test
    void Testsetcanceledreason() {
        transaction.setcanceledreason("timeout");
        assertEquals(transaction.getcanceledreason(),"timeout");
    }

    @Test
    void Testaddtocartandtostring(){
        transaction.add_to_cart(p);
        List<String> ls = new ArrayList<>();
        ls.add("Sprite,10");
        assertEquals(transaction.to_string(),ls);
    }

    @Test
    void Testaddmoreitemstocart(){
        transaction.add_to_cart(p);
        transaction.add_to_cart(p2);
        List<String> ls = new ArrayList<>();
        ls.add("Sprite,10");
        ls.add("Pepsi,10");
        assertEquals(transaction.to_string(),ls);
    }

    @Test
    void Testaddsameitemstocart(){
        transaction.add_to_cart(p);
        transaction.add_to_cart(p3);
        List<String> ls = new ArrayList<>();
        ls.add("Sprite,11");
        assertEquals(transaction.to_string(),ls);
    }

    @Test
    void Testgettotalpricewithnoitem(){
        assertEquals(transaction.gettotalprice(),0.0);
    }

    @Test
    void Testgettotalpricewithitems(){
        transaction.add_to_cart(p);
        assertEquals(transaction.gettotalprice(),10.0);
    }

    @Test
    void Testlistitems(){
        PrintStream standardOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        transaction.add_to_cart(p);
        transaction.list_items();
        assertEquals(outContent.toString(),"Shopping cart:\nproduct name,quantity\nSprite,10\ntotal price: 10.0\n");

    }

    @Test
    void Testlistitemswithnothing(){
        PrintStream standardOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        transaction.list_items();
        assertEquals(outContent.toString(),"Shopping cart:\nempty\ntotal price: 0.0\n");
    }

    @Test
    void testsuccesstransaction(){
        transaction.success();
        assertTrue(true);
    }


}
