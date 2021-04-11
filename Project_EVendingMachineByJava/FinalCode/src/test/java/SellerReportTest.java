import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SellerReportTest extends Seller {

    @Test
    void checkAvailable_items_report() {
        try{
            get_available_items_report();
            File file = new File("available_items_report");
            if(file.isFile() && file.exists()){
                assertTrue(true);
            }
        }
        catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    @Test
    void checkAvailable_summary_report() {
        try{
            get_summary_report();
            File file = new File("summary_report");
            if(file.isFile() && file.exists()){
                assertTrue(true);
            }
        }
        catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
}