package com.example.shopSpring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "orders")
@Entity
@Getter
@Setter
public class Order {

    @Id
    private Integer id;

    private LocalDate orderDate;

    private Double totalPrice;

    public Order() {
    }

    public Order(Integer id, LocalDate orderDate, Double totalPrice) {
        this.id = id;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }
}
