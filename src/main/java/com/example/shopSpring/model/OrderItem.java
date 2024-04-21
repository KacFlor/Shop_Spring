package com.example.shopSpring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
public class OrderItem {

    @Id
    private Integer id;

    private LocalDate quantity;

    private Double price;

    public OrderItem() {
    }

    public OrderItem(Integer id, LocalDate quantity, Double price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }
}
