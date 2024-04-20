package com.example.demo.Shipment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
public class Shipment {

    private Integer id;
    private LocalDate shipmentDate;
    private String address;
    private String city;
    private String state;
    private String country;
    private String zipcode;

    public Shipment() {
    }

    public Shipment(Integer id, LocalDate shipmentDate, String address, String city, String state, String country, String zipcode) {
        this.id = id;
        this.shipmentDate = shipmentDate;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipcode = zipcode;
    }
}
