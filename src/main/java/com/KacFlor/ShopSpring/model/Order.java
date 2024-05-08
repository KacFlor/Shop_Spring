package com.KacFlor.ShopSpring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shipment_id")
    @JsonBackReference
    private Shipment shipment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    @JsonManagedReference
    private Customer customer;

    @OneToMany(mappedBy = "order")
    @JsonBackReference
    private List<OrderItem> orderItems;


    public Order(){
    }

    public Order(LocalDate orderDate, Double totalPrice){
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }
}
