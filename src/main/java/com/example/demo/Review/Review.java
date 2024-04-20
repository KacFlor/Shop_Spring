package com.example.demo.Review;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter

public class Review {

    private Integer id;
    private Integer customerId;
    private Integer productId;
    private Integer rating;
    private String comment;

    public Review() {
    }

    public Review(Integer id, Integer customerId, Integer productId, Integer rating, String comment) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
    }
}
