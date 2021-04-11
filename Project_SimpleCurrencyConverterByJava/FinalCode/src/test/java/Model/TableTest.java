package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TableTest {
    Table table;
    String dateNowStr;
    String yesterdayStr;
    Time yesterdayTime;
    @BeforeEach
    public void setup(){
        //Set today
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dateNowStr = sdf.format(today);


        //Set yesterday
        Calendar calendar =Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        Date yesterday = calendar.getTime();
        yesterdayStr = sdf.format(yesterday);

        //Set all things today
        List<Currency> currencies = new ArrayList<>();
        Currency usd = new CurrencyImpl("USD");
        Currency aud = new CurrencyImpl("AUD");
        Currency gbp = new CurrencyImpl("GBP");
        Currency cny = new CurrencyImpl("CNY");

        currencies.add(usd);
        currencies.add(aud);
        currencies.add(gbp);
        currencies.add(cny);


        List<Exchange> exchanges = new ArrayList<>();

        Exchange usdTOaud = new ExchangeImpl(usd,aud,2);
        Exchange audTOusd= new ExchangeImpl(aud,usd,1.3);
        Exchange usdTOgbp = new ExchangeImpl(usd,gbp,4.0);
        Exchange gbpTOusd= new ExchangeImpl(gbp,usd,0.4);
        Exchange usdTOcny=new ExchangeImpl(usd,cny,2);
        Exchange cnyTOusd=new ExchangeImpl(cny,usd,3.5);
        Exchange audTOgbp= new ExchangeImpl(aud,gbp,1.2);
        Exchange gbpTOaud = new ExchangeImpl(gbp,aud,4.5);
        Exchange audTOcny= new ExchangeImpl(aud,cny,4.87727);
        Exchange cnyTOaud = new ExchangeImpl(cny,aud,3.5);

        exchanges.add(usdTOaud);
        exchanges.add(audTOusd);
        exchanges.add(usdTOgbp);
        exchanges.add(gbpTOusd);
        exchanges.add(usdTOcny);
        exchanges.add(cnyTOusd);
        exchanges.add(audTOgbp);
        exchanges.add(gbpTOaud);
        exchanges.add(audTOcny);
        exchanges.add(cnyTOaud);

        List<Time> times = new ArrayList<>();

        Time nowadayTime = new TimeImpl(dateNowStr, exchanges);

        times.add(nowadayTime);

        //Set all things yesterday
        List<Currency> currencies2 = new ArrayList<>();

        currencies2.add(usd);
        currencies2.add(aud);
        currencies2.add(gbp);

        List<Exchange> exchanges2 = new ArrayList<>();

        Exchange audTOusd2 = new ExchangeImpl(aud,usd,0.5);
        Exchange usdTOgbp2 = new ExchangeImpl(usd,gbp,4.5);
        Exchange gbpTOusd2 = new ExchangeImpl(gbp,usd,0.4);
        Exchange audTOgbp2 = new ExchangeImpl(aud,gbp,1.2);

        exchanges2.add(audTOusd2);
        exchanges2.add(usdTOgbp2);
        exchanges2.add(gbpTOusd2);
        exchanges2.add(audTOgbp2);


        yesterdayTime = new TimeImpl(yesterdayStr, exchanges2);

        times.add(yesterdayTime);


        // Table set
        table = new Table(dateNowStr,currencies,nowadayTime);

    }

    @Test
    void CompareTest(){
        Currency usd = new CurrencyImpl("USD");
        Currency aud = new CurrencyImpl("AUD");
        Currency gbp = new CurrencyImpl("GBP");
        Currency cny = new CurrencyImpl("CNY");
        Exchange audTOusd= new ExchangeImpl(aud,usd,1.3);
        Exchange audTOusd2 = new ExchangeImpl(aud,usd,0.5);

        Table t = new Table(null,null,null);
        String resultHigher = t.compare(audTOusd.getRate(),audTOusd2.getRate());
        assertEquals("1.30 ↑",resultHigher);

        Exchange usdTOgbp = new ExchangeImpl(usd,gbp,4.0);
        Exchange usdTOgbp2 = new ExchangeImpl(usd,gbp,4.5);
        String resultLower = t.compare(usdTOgbp.getRate(),usdTOgbp2.getRate());
        assertEquals("4.00 ↓",resultLower);

        Exchange cnyTOaud = new ExchangeImpl(cny,aud,3.5);
        Exchange cnyTOaud2 = new ExchangeImpl(cny,aud,3.5);
        String resultEqual = t.compare(cnyTOaud.getRate(),cnyTOaud2.getRate());
        assertEquals("3.50",resultEqual);

    }

    @Test
    void getDateTest(){
        String date = table.getDate();
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(today);
        assertEquals(dateNowStr,date);
    }

    @Test
    void displayNomalTest(){
        table.display(4);
    }

    @Test
    void getYesterdayTimeTest(){
        table.setYesterdayTime(yesterdayTime);
        Time yesterdayTest = table.getYesterdayTime();
        String dateTest = yesterdayTest.getDate();
        assertEquals(yesterdayStr,dateTest);
    }

    @Test
    void displayWithYesterdayDataAdded(){
        table.setYesterdayTime(yesterdayTime);
        table.display(4);
    }

}
