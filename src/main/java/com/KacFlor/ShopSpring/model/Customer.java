package com.KacFlor.ShopSpring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity{

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private Number phoneNumber;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JsonBackReference
    private List<CardData> cardDatas;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Shipment> shipments;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Review> reviews;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    @JsonManagedReference
    private Cart cart;

    @OneToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn(name = "wishlist_id")
    @JsonManagedReference
    private Wishlist wishlist;

    public Customer(String firstName, String lastName, String email, String address){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
    }

    public Customer(String firstName){
        this.firstName = firstName;
    }
}
