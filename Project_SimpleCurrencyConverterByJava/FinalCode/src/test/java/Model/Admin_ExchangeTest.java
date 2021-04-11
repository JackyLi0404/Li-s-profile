//https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
package Model;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

public class Admin_ExchangeTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    List<Time> times;
    List<Currency> currencies;
    List<Exchange> exchanges;
    AdminMenu am;

    @BeforeEach
    public void setUp() {
        times = new ArrayList<>();
        currencies = new ArrayList<>();
        exchanges = new ArrayList<>();
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//first day
        String dateNowStr = sdf.format(today);
        Time timeTest = new TimeImpl(dateNowStr, exchanges);//2020-10-07
        times.add(timeTest);
    }

    @Test
    public void simple_error_add() {
        currencies.add(Builder.buildCurrency("AUD"));
        currencies.add(Builder.buildCurrency("CNY"));
        boolean output=am.helper_add_exchange("AUD,CNY,5asda", times, currencies);
        assertFalse(output);
    }
    @Test
    public void simpleadd() {
        currencies.add(Builder.buildCurrency("AUD"));
        currencies.add(Builder.buildCurrency("CNY"));
        am.helper_add_exchange("AUD,CNY,5", times, currencies);
        int size = times.size();
        assertEquals(1, size);
        assertEquals(2, times.get(size - 1).getExchanges().size());
    }
    @Test
    public void simpleadd_withreplace() {
        currencies.add(Builder.buildCurrency("AUD"));
        currencies.add(Builder.buildCurrency("CNY"));
        am.helper_add_exchange("AUD,CNY,5", times, currencies);
        am.helper_add_exchange("AUD,CNY,6", times, currencies);
        int size = times.size();
        assertEquals(1, size);
        assertEquals(2, times.get(size - 1).getExchanges().size());
    }
    @Test
    public void simpleadd_withsameexhcange() {
        currencies.add(Builder.buildCurrency("AUD"));
        currencies.add(Builder.buildCurrency("CNY"));
        am.helper_add_exchange("AUD,CNY,5", times, currencies);
        am.helper_add_exchange("AUD,CNY,5", times, currencies);
        int size = times.size();
        assertEquals(1, size);
        assertEquals(2, times.get(size - 1).getExchanges().size());
    }

    @Test
    public void same_currency_name_inexchange() {
        currencies.add(Builder.buildCurrency("CNY"));
        currencies.add(Builder.buildCurrency("AUD"));
        boolean output = am.helper_add_exchange("AUD,AUD,5", times, currencies);
        boolean output2 = am.helper_add_exchange("CNY,CNY,5", times, currencies);
        assertFalse(output);
        assertFalse(output2);
    }

    @Test
    public void miss_both_currency() {//bo
        boolean output = am.helper_add_exchange("AUD,CNY,5", times, currencies);
        assertFalse(output);
    }

    @Test
    public void miss_form_currency() {
        currencies.add(Builder.buildCurrency("CNY"));
        boolean output = am.helper_add_exchange("AUD,CNY,5", times, currencies);
        assertFalse(output);
    }

    @Test
    public void miss_to_currency() {//bo
        currencies.add(Builder.buildCurrency("AUD"));
        boolean output = am.helper_add_exchange("AUD,CNY,5", times, currencies);
        assertFalse(output);
    }

    @Test
    public void input_inlegal() {//bo
        currencies.add(Builder.buildCurrency("AUD"));
        boolean output = am.helper_add_exchange("AUD,CNY,5,2334", times, currencies);
        assertFalse(output);
    }
}