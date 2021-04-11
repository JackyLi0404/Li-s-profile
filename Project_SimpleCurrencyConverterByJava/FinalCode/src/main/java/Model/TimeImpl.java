package Model;

import java.util.ArrayList;
import java.util.List;

class TimeImpl implements Time {
    private final String date;
    private List<Exchange> exchanges;

    public TimeImpl(String date, List<Exchange> exchanges){
        this.date = date;
        this.exchanges = exchanges;

    }

    @Override
    public List<Exchange> getExchanges() {
        return exchanges;
    }

    @Override
    public Exchange getExchange(Currency fromCurrency, Currency toCurrency) {
        if (fromCurrency == null || toCurrency == null) {
            return null;
        }
        for (Exchange ex : exchanges) {
            if (ex.getFromCurrency().equals(fromCurrency) && ex.getToCurrency().equals(toCurrency)) {
                return ex;
            }
        }
        return null;
    }

    @Override
    public void addExchange(Exchange exchange) {
        exchanges.removeIf(x -> x.getFromCurrency().equals(exchange.getFromCurrency()) && x.getToCurrency().equals(exchange.getToCurrency()));
        exchanges.add(exchange);
    }

    @Override
    public void deleteExchange(Currency fromCurrency, Currency toCurrency) {
        exchanges.removeIf(x -> x.getFromCurrency().equals(fromCurrency) && x.getToCurrency().equals(toCurrency));
    }

    public String getDate() {
        return date;
    }
}
