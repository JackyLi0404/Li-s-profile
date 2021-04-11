package Model;

import java.util.Objects;

class ExchangeImpl implements Exchange {
    private final Currency fromCurrency;
    private final Currency toCurrency;
    private final double rate;

    ExchangeImpl(Currency fromCurrency, Currency toCurrency, double rate) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.rate = rate;
    }

    @Override
    public Currency getFromCurrency() {
        return fromCurrency;
    }

    @Override
    public Currency getToCurrency() {
        return toCurrency;
    }

    @Override
    public double getRate() {
        return rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeImpl exchange = (ExchangeImpl) o;
        return Double.compare(exchange.rate, rate) == 0 &&
                Objects.equals(fromCurrency, exchange.fromCurrency) &&
                Objects.equals(toCurrency, exchange.toCurrency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromCurrency, toCurrency, rate);
    }

    @Override
    public String toString() {
        return "ExchangeImpl{" +
                "fromCurrency=" + fromCurrency +
                ", toCurrency=" + toCurrency +
                ", rate=" + rate +
                '}';
    }
}
