package com.KacFlor.ShopSpring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
public class OrderItem extends BaseEntity{

    @Column(name = "quantity")
    private LocalDate quantity;

    @Column(name = "price")
    private Double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    public OrderItem() {
    }

    public OrderItem(LocalDate quantity, Double price) {
        this.quantity = quantity;
        this.price = price;
    }
}
