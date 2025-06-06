package sk.streetofcode.productordermanagement.implementation.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductEntity {
    @Id
    @SequenceGenerator(name = "product_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    private Long id;

    @Column(nullable = false, unique = true)
    @Setter
    private String name;

    @Column(nullable = true)
    @Setter
    private String description;

    @Column
    @Setter
    private long amount;

    @Column(nullable = false)
    @Setter
    private double price;


    public ProductEntity(String name, String description, long amount, double price) {
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.price = price;
    }

}
