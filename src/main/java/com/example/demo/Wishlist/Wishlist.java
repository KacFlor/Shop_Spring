package com.example.demo.Wishlist;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Wishlist {

    private Integer id;

    public Wishlist(Integer id) {
        this.id = id;
    }

    public Wishlist() {
    }
}
