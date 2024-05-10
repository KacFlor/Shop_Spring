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
public class Cart extends BaseEntity{

    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Product> products;

    @Column(name = "quantity")
    private Integer quantity;

    @OneToOne(mappedBy = "cart", fetch = FetchType.EAGER)
    @JsonBackReference
    private Customer customer;


    public Cart(){
    }

    public Cart(Integer quantity){
        this.quantity = quantity;
    }
}
