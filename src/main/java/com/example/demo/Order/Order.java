package com.example.demo.Order;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Table
@Entity
@Getter
@Setter
public class Order {

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
