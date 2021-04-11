package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TimeImplTest {
    List<Exchange> exchanges;
    Time time;
    String date;
    Exchange exchange1;
    Exchange exchange2;
    Exchange exchange3;

    @BeforeEach
    public void setUp() {
        date = "2020-05-20";
        exchanges = new ArrayList<>();
        exchange1 = Builder.buildExchange("usd", "pd", 1);
        exchange2 = Builder.buildExchange("pd", "au", 1);
        exchange3 = Builder.buildExchange("jp", "au", 1);
        exchanges.add(exchange1);
        exchanges.add(exchange2);
        exchanges.add(exchange3);
        time = new TimeImpl(date, exchanges);
    }

    @Test
    void getExchanges() {
        List<Exchange> dateExchanges = time.getExchanges();
        assertEquals(dateExchanges.size(), exchanges.size());
        for (Exchange ex : exchanges) {
            assertTrue(dateExchanges.contains(ex));
        }
    }

    @Test
    void getExchange() {
        assertEquals(time.getExchange(exchange1.getFromCurrency(), exchange1.getToCurrency()), exchange1);
        assertEquals(time.getExchange(Builder.buildCurrency("pd"), exchange2.getToCurrency()), exchange2);
        assertNull(time.getExchange(null, null));
        assertNull(time.getExchange(null, exchange1.getToCurrency()));
        assertNull(time.getExchange(exchange2.getFromCurrency(), Builder.buildCurrency("hkd")));
        assertNull(time.getExchange(exchange2.getFromCurrency(), null));
    }

    @Test
    void addExchange() {
        Exchange exchange = Builder.buildExchange("hkd", "usd", 2);
        assertFalse(time.getExchanges().contains(exchange));
        time.addExchange(exchange);
        assertEquals(time.getExchange(Builder.buildCurrency("hkd"), Builder.buildCurrency("usd")), exchange);
        assertTrue(time.getExchanges().contains(exchange));
        time.addExchange(Builder.buildExchange("hkd", "usd", 1));
    }


    @Test
    void getDate() {
        assertEquals(time.getDate(), date);
    }

    @Test
    void deleteExchange() {
        int previous_number = time.getExchanges().size();
        assertEquals(exchange1, time.getExchange(exchange1.getFromCurrency(), exchange1.getToCurrency()));
        assertTrue(time.getExchanges().contains(exchange1));
        time.deleteExchange(exchange1.getFromCurrency(), exchange1.getToCurrency());
        assertEquals(time.getExchanges().size(), previous_number - 1);
        assertFalse(time.getExchanges().contains(exchange1));

    }
}