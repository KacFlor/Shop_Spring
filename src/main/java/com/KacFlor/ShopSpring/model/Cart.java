package com.KacFlor.ShopSpring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Cart extends BaseEntity{

    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER)
    private List<Product> products;

    @Column(name = "quantity")
    private Integer quantity;

    @OneToOne(mappedBy = "cart", fetch = FetchType.EAGER)
    private Customer customer;


    public Cart(){
    }

    public Cart(List<Product> products){
        this.products = products;
    }
}
