import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {
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

}
