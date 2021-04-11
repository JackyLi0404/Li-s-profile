package Model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
public class AdminmenuTest {
    List<Currency> currencies;
    List<Exchange> exchanges;
    List<Exchange> exchanges2;
    List<Time> times;
    AdminMenu menu;
    SimpleDateFormat sdf;

    private static ByteArrayInputStream in;
    @BeforeEach
    public void setup(){
        menu = new AdminMenu();
        currencies = new ArrayList<>();
        times = new ArrayList<>();
        exchanges = new ArrayList<>();
        exchanges2 = new ArrayList<>();

        Date today = new Date();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(today);

        //set currencies
        Currency usd = new CurrencyImpl("USD");
        Currency aud = new CurrencyImpl("AUD");
        Currency cny = new CurrencyImpl("CNY");
        Currency jp = new CurrencyImpl("JP");
        Currency eu = new CurrencyImpl("EU");

        currencies.add(usd);
        currencies.add(aud);
        currencies.add(cny);
        currencies.add(jp);
        currencies.add(eu);

        //set exchanges
        Exchange audTOusd= new ExchangeImpl(aud,usd,1.5);
        exchanges.add(audTOusd);
        exchanges2.add(new ExchangeImpl(aud,usd,1.3));
        exchanges2.add(new ExchangeImpl(usd,aud,1.1));

        Time timeTest = new TimeImpl("2020-10-05", exchanges);
        times.add(timeTest);
        times.add(new TimeImpl("2020-09-05", exchanges2));




    }
    @Test
    void TestConvert(){
        String converted = AdminMenu.convert("AUD","USD", times,100.0);
        assertEquals("150.00", converted);
    }

    @Test
    void TestConvertEmptytime(){
        List<Time> notimes = new ArrayList<>();
        String converted = AdminMenu.convert("AUD","USD", notimes,100.0);
        assertNull(converted);
    }

    @Test
    void TestConvertNoExchange(){
        String converted = AdminMenu.convert("USD","AUD", times,100.0);
        assertEquals("No such exchange rate between USD and AUD", converted);
    }

    @Test
    void TestSummary(){
        PrintStream standardOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try{
            AdminMenu.conversionsummary(sdf.parse("2020-01-01"),sdf.parse("2021-01-01"),"AUD","USD",times);
        }catch(ParseException e){
            System.out.println(e);
        }
        assertEquals("2020-09-05 1.3\n2020-10-05 1.5\naverage rate: 1.40\nmedian rate: 1.40\nmax rate: 1.50\nmin rate: 1.30\nstandard deviation of rates: 0.10\n",outContent.toString());
    }

    @Test
    void TestSummaryEmptytime(){
        PrintStream standardOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        List<Time> notimes = new ArrayList<>();
        try{
            AdminMenu.conversionsummary(sdf.parse("2020-01-01"),sdf.parse("2021-01-01"),"AUD","USD",notimes);
        }catch(ParseException e){
            System.out.println(e);
        }
        assertEquals("No data in database, please contact admin for help.\n",outContent.toString());
    }

    @Test
    void TestSummaryWrongperiod(){
        PrintStream standardOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        List<Time> notimes = new ArrayList<>();
        try{
            AdminMenu.conversionsummary(sdf.parse("2021-01-01"),sdf.parse("2020-01-01"),"AUD","USD",times);
        }catch(ParseException e){
            System.out.println(e);
        }
        assertEquals("The end date is earlier than start date, no such period exist, please enter correct period.\n",outContent.toString());
    }

    @Test
    void TestSummaryNoexchanegs(){
        PrintStream standardOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        List<Time> notimes = new ArrayList<>();
        try{
            AdminMenu.conversionsummary(sdf.parse("2021-01-01"),sdf.parse("2022-01-01"),"AUD","USD",times);
        }catch(ParseException e){
            System.out.println(e);
        }
        assertEquals("No exchange rate from AUD to USD between 2021-01-01 and 2022-01-01.\n",outContent.toString());
    }

    @Test
    void getEditedCurrenciesTest(){
        List<Currency> edited = AdminMenu.getEditedCurrencies(currencies);
        int size = edited.size();
        assertEquals(4,size);
    }


}
