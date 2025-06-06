package sk.streetofcode.productordermanagement.domain;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Order {
    private final long id;
    private final Long shoppingList;
    private final boolean paid;
    private final long amount;
    private final double totalprice;
    private final OffsetDateTime createdAt;

    public Order(long id, Long shoppingList, boolean paid, long amount, double price, OffsetDateTime createdAt) {
        this.id = id;
        this.shoppingList = shoppingList;
        this.paid = paid;
        this.amount = amount;
        this.totalprice = price;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public Long getShoppingList() {
        return shoppingList;
    }

    public boolean isPaid() {
        return paid;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public long getAmount() {
        return amount;
    }
}
