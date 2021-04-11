import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductMenuTest {
    ProductMenu m;
    Product juice = new Product("juice","1004", "Drinks", 4,15, 0);

    @BeforeEach
    public void CategoryCreateTest(){
        Product cola = new Product("Cola", "1001", "Drinks",3,10,0);
        Product sprite = new Product("sprite", "1002", "Drinks", 3.5,15, 0);
        List<Product> items = new ArrayList<>(Arrays.asList(cola,sprite));
        m = new ProductMenu("drinks",items);
    }

    @Test
    public void getCategTest(){
        String cate = m.getCategoryName();
        assertEquals("drinks",cate);
    }

    @Test
    public void getProTest(){
        List<Product> is  = m.getProducts();
        assertEquals("Cola",is.get(0).getName());
    }

    @Test
    public void addProductTest(){
        m.addProduct(juice);

    }

}
