import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {
    static List<Product> items;
    static List<Product> drinkList;
    static List<Product> chocList;
    static List<Product> chipList;
    static List<Product> candyList;
    static ProductMenu drinks;
    static ProductMenu chocolates;
    static ProductMenu chips;
    static ProductMenu candies;
    static List<ProductMenu> test;

    private static final InputStream DEFAULT_STDIN = System.in;


    @AfterEach
    public void rollbackChangesToStdin() {
        System.setIn(DEFAULT_STDIN);
    }

    @Test
    public void helperTest(){
        App.helper();
    }
    @Test
    public void readItemTest(){
        Connection c = sqliteFunction.connect();
        List<Product> items = App.readItem(c);
        try{
            c.close();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        assertEquals(14, items.size());
    }



    @Test
    public void WholeInterfaceTest(){


//        String linesp = System.lineSeparator();
        String customer = "2\nwrong\nwrong\n2\nleft\nleft\n1\n1\n2\n1\n1\nJuice\n1\n2\n4\n3\n4\n4\n4\n7\n3\n";
        String seller = "2\nseller\nseller\n1\n4002\napple\n1\n4002\nSour\n2\nJuice\n999\n2\nJuice\n1004\n3\nJuice\nChips\n3\nJuice\nDrinks\n4\nJuice\n16\n4\nJuice\n12\n4\nJuice\n7\n5\nJuice\n999\n5\nJuice\n2.5\n6\n7\n8\n";
        String c = "2\ncashier\ncashier\n1\n7\n1\n5\n2\n7\n2\n5\n3\n7\n3\n5\n4\n7\n4\n5\n5\n7\n5\n5\n6\n7\n6\n5\n7\n7\n7\n5\n8\n7\n8\n5\n9\n7\n9\n5\n10\n7\n10\n5\n11\n7\n11\n5\n12\n13\n14\n";
        String o = "2\nadmin\nadmin\n1\nright\nright\nowner\n1\nwww\nwww\nowner\n2\nwww\n2\neee\n3\n8\n4\n14\n5\n6\n7\n";
        String x = "3\n2\n1\n1\nJuice\n1\n6\n1\n1\n0\n0\n0\n1\n0\n0\n0\n0\n0\n0\n0\n4";


        ByteArrayInputStream in2 = new ByteArrayInputStream((customer + seller + c + o + x).getBytes());
        System.setIn(in2);
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
        App.main(null);
        assertTrue(true);


    }


}
