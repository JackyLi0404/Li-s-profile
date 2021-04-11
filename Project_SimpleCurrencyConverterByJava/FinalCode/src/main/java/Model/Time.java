package Model;

import java.util.List;

public interface Time {
    /**
     * @return a list of changes of the day
     */
    public List<Exchange> getExchanges();

    /**
     * @param fromCurrency from
     * @param toCurrency   to
     * @return null if target exchange does not exist, true if exist
     */
    public Exchange getExchange(Currency fromCurrency, Currency toCurrency);

    /**
     * if exchanges of the same from currency and to currency already exit, replace it
     *
     * @param exchange new exchange to be added
     */
    public void addExchange(Exchange exchange);

    /**
     * delte an exchange
     *
     * @param fromCurrency from
     * @param toCurrency   to
     */
    public void deleteExchange(Currency fromCurrency, Currency toCurrency);


    /**
     * @return date as string
     */
    public String getDate();
}
