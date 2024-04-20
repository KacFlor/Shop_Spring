package com.example.demo.OrderItem;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
public class OrderItem {

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
