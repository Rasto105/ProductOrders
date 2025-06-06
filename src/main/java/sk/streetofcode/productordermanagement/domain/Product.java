package sk.streetofcode.productordermanagement.domain;

import lombok.Value;

import java.math.BigDecimal;
@Value
public class Product {
    long id;
    String name;
    String description;
    long amount;
    double price;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public long getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }
}
