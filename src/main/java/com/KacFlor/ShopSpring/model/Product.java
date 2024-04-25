package com.KacFlor.ShopSpring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Product extends BaseEntity{

    @Column(name = "sku")
    @NotBlank
    private String sku;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    @NotBlank
    private Double price;

    @Column(name = "stock")
    @NotBlank
    private Integer stock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<Promotion> promotions;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<Wishlist> wishlists;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<Review> reviews;

    public Product(){
    }

    public Product(String sku, String description, Double price, Integer stock, Category category, Supplier supplier, Promotion promotion){
        this.sku = sku;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.supplier = supplier;
        this.promotions = promotions;
    }
}
