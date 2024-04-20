package com.example.demo.Supplier;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Supplier {

    private Integer id;
    private String name;

    public Supplier() {
    }

    public Supplier(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
