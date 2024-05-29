package com.KacFlor.ShopSpring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class OrderItem extends BaseEntity{

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "price")
    private Double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @JsonManagedReference
    private Product product;

    public OrderItem() {
    }

    public OrderItem(String name, Double quantity, Double price){
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

}
