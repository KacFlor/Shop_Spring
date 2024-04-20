package com.example.demo.Review;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table
public class Review {
    private Integer id;
    private Integer customer_id;
    private Integer product_id;
    private Integer rating;
    private String comment;

    public Review() {
    }

    public Review(Integer id, Integer customer_id, Integer product_id, Integer rating, String comment) {
        this.id = id;
        this.customer_id = customer_id;
        this.product_id = product_id;
        this.rating = rating;
        this.comment = comment;
    }
}
