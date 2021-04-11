package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuilderTest {

    @Test
    void buildCurrency() {
        assertEquals(Builder.buildCurrency("usd").getName(), "usd");
    }

    @Test
    void buildExchange() {
        Exchange exchange = Builder.buildExchange("usd", "hkd", 10);
        assertEquals(exchange.getFromCurrency(), Builder.buildCurrency("usd"));
        assertEquals(exchange.getToCurrency(), Builder.buildCurrency("hkd"));
        assertEquals(exchange.getRate(), 10);
    }

}