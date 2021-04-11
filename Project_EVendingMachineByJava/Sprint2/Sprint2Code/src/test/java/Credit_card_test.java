import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Credit_card_test extends Credit_card {


    @Test
    void checkCardValid_cardholder_invalid() {
        assertFalse(checkvalid( "admin","Jacky", "40691"));
    }

    @Test
    void checkCardValid_cardnum_invalid() {
        assertFalse(checkvalid("admin","Charles", "8465621"));
    }

    @Test
    void checkCardValid_invalid(){
        assertFalse(checkvalid("admin","Jacky", "8465621"));
    }

    @Test
    void checkCardValidAnonymous_valid(){ boolean valid = checkvalid("anonymous","Charles", "40691"); assertTrue(valid);}

    @Test
    void checkSaved_unsaved() { assertFalse(checksaved("admin" )); }

    @Test
    void checkSaved_saved() { assertTrue(checksaved("right" )); }


}
