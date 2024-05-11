package com.KacFlor.ShopSpring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table()
@Getter
@Setter
public class Wishlist extends BaseEntity{


    @OneToOne(mappedBy = "wishlist", fetch = FetchType.EAGER)
    @JsonBackReference
    private Customer customer;

    @OneToMany(mappedBy = "wishlist", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Product> products;

    public Wishlist(){
    }
}
