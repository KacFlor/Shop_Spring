package com.KacFlor.ShopSpring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Table(name = "orders")
@Entity
@Getter
@Setter
public class Order extends BaseEntity{

    @Column(name = "orderDate")
    private LocalDate orderDate;

    @Column(name = "totalPrice")
    private Double totalPrice;

    @OneToOne(mappedBy = "order", fetch = FetchType.EAGER)
    private Shipment shipment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;



    public Order() {
    }

    public Order(LocalDate orderDate, Double totalPrice) {
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }
}
