package com.KacFlor.ShopSpring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Customer extends BaseEntity{

    @Column(name = "firstName")
    @NotBlank
    private String firstName;

    @Column(name = "lastName")
    @NotBlank
    private String lastName;

    @Column(name = "email")
    @NotBlank
    private String email;

    @Column(name = "address")
    @NotBlank
    private String address;

    @Column(name = "phone")
    private Number phoneNumber;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Order> orders;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<CardData> cardDatas;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Shipment> shipments;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Review> reviews;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "wishlist_id")
    private Wishlist wishlist;


    public Customer() {
    }

    public Customer(String firstName, String lastName, String email, String address, Number phoneNumber, List<Order> orders, List<CardData> cardDatas) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.orders = orders;
        this.cardDatas = cardDatas;
    }
}
