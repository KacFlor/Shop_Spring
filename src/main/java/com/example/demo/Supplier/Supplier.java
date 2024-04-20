package com.example.demo.Supplier;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table
public class Supplier {
    private Integer id;
    private String name;

    public Supplier() {
    }

    public Supplier(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
