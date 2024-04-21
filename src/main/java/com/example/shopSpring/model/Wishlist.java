package com.example.shopSpring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table()
@Getter
@Setter
public class Wishlist {

    @Id
    private Integer id;

    public Wishlist(Integer id) {
        this.id = id;
    }

    public Wishlist() {
    }
}
