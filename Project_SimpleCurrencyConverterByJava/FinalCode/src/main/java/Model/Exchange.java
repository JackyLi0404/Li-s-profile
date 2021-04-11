package Model;

public interface Exchange {
    public Currency getFromCurrency();

    public Currency getToCurrency();

    public double getRate();
}
