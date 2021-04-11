package Model;

public class Builder {
    public static Currency buildCurrency(String name) {
        return new CurrencyImpl(name);
    }

    public static Exchange buildExchange(String fromName, String toName, double rate) {
        return new ExchangeImpl(buildCurrency(fromName), buildCurrency(toName), rate);
    }
}
