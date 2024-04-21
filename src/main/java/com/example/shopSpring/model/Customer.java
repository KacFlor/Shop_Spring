package com.example.shopSpring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Customer {

    @Id
    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private Number phoneNumber;

    @OneToMany
    @JoinColumn
    private List<Order> orders;

    @OneToMany
    @JoinColumn
    private List<CardData> cardDatas;

    public Customer() {
    }

    public Customer(Integer id, String firstName, String lastName, String email, String address, Number phoneNumber, List<Order> orders, List<CardData> cardDatas) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.orders = orders;
        this.cardDatas = cardDatas;
    }
}
