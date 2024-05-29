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
public class Product extends BaseEntity{

    @Column(name = "sku")
    private String sku;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "stock")
    private Double stock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    @JsonManagedReference
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id")
    @JsonManagedReference
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    @JsonBackReference
    private Cart cart;

    @ManyToMany(mappedBy = "products", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Promotion> promotions;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<OrderItem> orderItems;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "wishlist_id")
    @JsonBackReference
    private Wishlist wishlist;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    @JsonBackReference
    private List<Review> reviews;

    public Product(){
    }

    public Product(String sku, String name, String description, Double price, Double stock){
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;

    }
}
