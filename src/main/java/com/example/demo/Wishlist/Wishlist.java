package com.example.demo.Wishlist;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table
public class Wishlist {
    private Integer id;

    public Wishlist(Integer id) {
        this.id = id;
    }

    public Wishlist() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
