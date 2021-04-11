package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyImplTest {

    @Test
    void getName() {
        CurrencyImpl usd = new CurrencyImpl("USD");
        assertEquals(usd.getName(), "USD");
    }

    @Test
    void equals() {
        CurrencyImpl usd = new CurrencyImpl("USD");
        CurrencyImpl usd1 = new CurrencyImpl("USD");
        assertEquals(usd, usd);
        assertEquals(usd, usd1);
    }

    @Test
    void HashCode() {
        CurrencyImpl usd = new CurrencyImpl("USD");
        CurrencyImpl usd1 = new CurrencyImpl("USD");
        assertEquals(usd.hashCode(), usd1.hashCode());
        assertEquals(usd.hashCode(), usd.hashCode());
    }

    @Test
    void testToString() {
        CurrencyImpl usd = new CurrencyImpl("USD");
        CurrencyImpl usd1 = new CurrencyImpl("USD");
        assertEquals(usd.toString(), usd1.toString());
    }
}