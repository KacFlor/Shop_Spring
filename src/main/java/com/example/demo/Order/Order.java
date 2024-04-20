package com.example.demo.Order;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table
public class Order {
    private Integer id;
    private LocalDate order_date;
    private Double total_price;

    public Order() {
    }

    public Order(Integer id, LocalDate order_date, Double total_price) {
        this.id = id;
        this.order_date = order_date;
        this.total_price = total_price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getOrder_date() {
        return order_date;
    }

    public void setOrder_date(LocalDate order_date) {
        this.order_date = order_date;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
    }
}
