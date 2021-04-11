package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExchangeImplTest {
    Exchange exchange;
    Currency fromCur;
    Currency toCur;
    double rate;

    @BeforeEach
    public void setUp() {
        fromCur = Builder.buildCurrency("usd");
        toCur = Builder.buildCurrency("au");
        rate = 0.1;
        exchange = new ExchangeImpl(fromCur, toCur, rate);
    }

    @Test
    void getFromCurrency() {
        assertEquals(exchange.getFromCurrency(), fromCur);
    }

    @Test
    void getToCurrency() {
        assertEquals(exchange.getToCurrency(), toCur);
    }

    @Test
    void getRate() {
        assertEquals(exchange.getRate(), rate);
    }

    @Test
    void equals() {
        assertEquals(exchange, new ExchangeImpl(fromCur, toCur, rate));
        assertEquals(exchange, new ExchangeImpl(Builder.buildCurrency("usd"), toCur, rate));
    }

    @Test
    void HashCode() {
        assertEquals(exchange.hashCode(), new ExchangeImpl(fromCur, toCur, rate).hashCode());
    }

    @Test
    void testToString() {

        assertEquals(exchange.toString(), new ExchangeImpl(fromCur, toCur, rate).toString());
    }
}