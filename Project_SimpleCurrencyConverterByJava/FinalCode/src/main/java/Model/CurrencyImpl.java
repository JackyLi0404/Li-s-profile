package Model;

import java.util.Objects;

class CurrencyImpl implements Currency {
    private final String name;

    CurrencyImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyImpl currency = (CurrencyImpl) o;
        return currency.name.equals(name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "CurrencyImpl{" +
                "name='" + name + '\'' +
                '}';
    }
}
