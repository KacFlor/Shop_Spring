package com.example.shopSpring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Supplier {

    @Id
    private Integer id;

    private String name;

    public Supplier() {
    }

    public Supplier(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
