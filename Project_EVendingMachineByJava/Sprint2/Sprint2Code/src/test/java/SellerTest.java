import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
public class SellerTest{
    String item_code;
    String item_name;
    String category;
    @BeforeEach
    public void setup(){
        item_code="4002";
        item_name="Juice";
        category="Chips";
        
    }
    @Test
    public void changeCategorySuccessfully(){
        boolean output= Seller.changeCategory(category,item_name);
        assertTrue(output);
        Seller.changeCategory("Drinks",item_name);//change back
    }
    @Test
    public void changeCategoryfailed(){
        boolean output= Seller.changeCategory(category,"chips");
        assertFalse(output);
    }
     @Test
    public void changeCategoryfailed2(){
        boolean output= Seller.changeCategory(category,"juice");
        assertFalse(output);
    }
    ///////////////////////////////////////////////////
    @Test
    public void changeNameSuccessfully(){
        boolean output=Seller.changeName(item_code,"apple");
        assertTrue(output);
        Seller.changeName(item_code,"Juice");
    }
    @Test
    public void changeNameFailed(){
        boolean output=Seller.changeName("9999",item_name);
        assertFalse(output);
    }
    ///////////////////////////////////////////////////
    @Test
    public void changeItem_code_Successfully(){
        boolean output=Seller.changeItemCode("99999999",item_name);
        assertTrue(output);
        Seller.changeItemCode(item_code,item_name);
    }
    @Test
    public void changeItem_code_failture(){
        boolean output=Seller.changeItemCode(item_code,"apple");
        assertFalse(output);
    }
    /////////////////////////////////////////////////////
    @Test
    public void changePriceSuccessfully(){
        boolean output=Seller.changePrice(987,item_name);
        assertTrue(output);
        Seller.changePrice(2.5,item_name);
    }
    @Test
    public void changePriceFailture(){
        boolean output=Seller.changePrice(987,"noitems");
        assertFalse(output);
    }
    
    //////////////////////////////////////////////////////
    @Test
    public void changeQuantitySuccessfully(){
        boolean output=Seller.changeQuantity(12,item_name);
        assertTrue(output);
        Seller.changeQuantity(7,item_name);
    }
    @Test
    public void changeQuantityFailture(){
        boolean output=Seller.changeQuantity(987,"noitems");
        assertFalse(output);
    }
    @Test
    public void changeQuantityFailture2(){
        boolean output=Seller.changeQuantity(16,"Juice");
        assertFalse(output);
    }
}