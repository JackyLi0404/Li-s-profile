import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    Product cola;
    Product sprite;
    @BeforeEach
    public void ProductCreateTest(){
       cola = new Product("Cola", "1001", "Drinks",3,10,0);
       sprite = new Product("sprite","1002", "Drinks", 3.5,15, 0);
    }

    @Test
    public void getNameTest(){
        String name = cola.getName();
        assertEquals("Cola",name);
    }

    @Test
    public void getCategory(){
        String category = cola.getCategory();
        assertEquals("Drinks",category);
    }

    @Test
    public void toStringTest(){
        String line = cola.toString();
        assertEquals("Item_code: 1001  Product: Cola  Inventory: 10  Price: 3.0",line);
    }

    @Test
    public void updateTest(){
        boolean s = cola.updateInventory(-1);
        assertFalse(s);
        boolean s2 = cola.updateInventory(50);
        assertFalse(s2);
        cola.updateInventory(1);
        assertEquals(9,cola.getInventory());
    }
}
