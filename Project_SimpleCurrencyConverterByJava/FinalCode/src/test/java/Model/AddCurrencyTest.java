package Model;

import org.junit.jupiter.api.*;
import java.util.*;
import java.text.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class AddCurrencyTest extends AdminMenu {
    private static List<Currency> currencies;

    @BeforeEach
    public void setUp(){
        currencies = new ArrayList<>();
        Currency usd = new CurrencyImpl("USD");
        Currency aud = new CurrencyImpl("AUD");
        Currency gbp = new CurrencyImpl("GBP");
        Currency cny = new CurrencyImpl("CNY");
        Currency eur = new CurrencyImpl("EUR");
        Currency inr = new CurrencyImpl("INR");
        currencies.add(usd);
        currencies.add(aud);
        currencies.add(gbp);
        currencies.add(cny);
        currencies.add(eur);
        currencies.add(inr);
    }

    @Test
    void addCurrency_normalCase() {
        helper_add_currency("JPY", currencies);
        assertEquals(currencies.size(), 7);
        assertEquals(currencies.get(currencies.size()-1).getName(), "JPY");
    }

    @Test
    void addCurrency_lotsOfInputsCase() {
        int length = currencies.size();

        helper_add_currency("JPY", currencies);
        helper_add_currency("FDGREFBFTHASDFGM", currencies);
        helper_add_currency("RUT", currencies);
        helper_add_currency("DFGRLEFRET", currencies);
        helper_add_currency("MA", currencies);
        helper_add_currency("GRSDKGBLEW", currencies);
        helper_add_currency("PTET", currencies);
        helper_add_currency("Z", currencies);
        helper_add_currency("TEDKGXCVBI", currencies);
        helper_add_currency("TESDLGMDPXWETSDKGDNCVEDIE", currencies);

        assertEquals(currencies.get(length).getName(), "JPY");
        assertEquals(currencies.get(length+1).getName(), "FDGREFBFTHASDFGM");
        assertEquals(currencies.get(length+2).getName(), "RUT");
        assertEquals(currencies.get(length+3).getName(), "DFGRLEFRET");
        assertEquals(currencies.get(length+4).getName(), "MA");
        assertEquals(currencies.get(length+5).getName(), "GRSDKGBLEW");
        assertEquals(currencies.get(length+6).getName(), "PTET");
        assertEquals(currencies.get(length+7).getName(), "Z");
        assertEquals(currencies.get(length+8).getName(), "TEDKGXCVBI");
        assertEquals(currencies.get(length+9).getName(), "TESDLGMDPXWETSDKGDNCVEDIE");
    }

    // @Test
    void addCurrency_usedCurrencyCase() {
        int length = currencies.size();
        helper_add_currency("USD", currencies);
        assertEquals(length, currencies.size());
    }
    // @Test
    // void addCurrency_multiSituationCase() {
    //     int length = currencies.size();

    //     helper_add_currency("JPY", currencies);
    //     helper_add_currency("547381", currencies);
    //     helper_add_currency("USD", currencies);
    //     helper_add_currency("AUD", currencies);
    //     helper_add_currency(" , ", currencies);
    //     helper_add_currency("PTET", currencies);
    //     helper_add_currency(" ", currencies);
    //     assertEquals(length + 2, currencies.size());
    //     assertEquals(currencies.get(length).getName(), "JPY");
    //     assertEquals(currencies.get(length + 1).getName(), "PTET");
    // }
}
