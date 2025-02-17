package com.KacFlor.ShopSpring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Cart extends BaseEntity {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @JsonManagedReference
    private List<Product> products;

    @ElementCollection
    @CollectionTable(
            name = "cart_product_quantity",
            joinColumns = @JoinColumn(name = "cart_id")
    )
    @Column(name = "quantity")
    private List<Integer> quantities;

    @OneToOne(mappedBy = "cart", fetch = FetchType.EAGER)
    @JsonBackReference
    private Customer customer;

    @Column(name = "price")
    private Double price = 0.0;

    public Cart() {}

    public Cart(Double price) {
        this.price = price;
    }
}
