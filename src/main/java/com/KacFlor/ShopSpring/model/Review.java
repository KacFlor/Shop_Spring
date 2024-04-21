package com.KacFlor.ShopSpring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter

public class Review extends BaseEntity{

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "comment")
    @NotBlank
    private String comment;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private  Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private  Product product;

    public Review() {
    }

    public Review(Integer rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }
}
