package com.KacFlor.ShopSpring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
public class Shipment extends BaseEntity{

    @Column(name = "shipmentDate")
    private LocalDate shipmentDate;

    @Column(name = "address")
    @NotBlank
    private String address;

    @Column(name = "city")
    @NotBlank
    private String city;

    @Column(name = "state")
    @NotBlank
    private String state;

    @Column(name = "country")
    @NotBlank
    private String country;

    @Column(name = "zipcode")
    @NotBlank
    private String zipcode;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Shipment() {
    }

    public Shipment(LocalDate shipmentDate, String address, String city, String state, String country, String zipcode) {
        this.shipmentDate = shipmentDate;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipcode = zipcode;
    }
}
