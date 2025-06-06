package sk.streetofcode.productordermanagement.implementation.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity(name = "order")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    @Id
    @SequenceGenerator(name = "order_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @Setter
    private ProductEntity product;
    @Column()
    @Setter
    private long amount;
    @Column()
    @Setter
    private double totalPrice;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Setter
    private boolean paid = false;

    @Column(nullable = false)
    @Setter
    private OffsetDateTime createdAt;

    public OrderEntity(ProductEntity product, long amount, double totalPrice, boolean paid, OffsetDateTime createdAt) {
        this.product = product;
        this.amount = amount;
        this.totalPrice = totalPrice;
        this.paid = paid;
        this.createdAt = createdAt;
    }
}
