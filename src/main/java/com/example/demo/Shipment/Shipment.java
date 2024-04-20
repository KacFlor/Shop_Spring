package com.example.demo.Shipment;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table
public class Shipment {
    private Integer id;
    private LocalDate shipment_date;
    private String address;
    private String city;
    private String state;
    private String country;
    private String zipcode;

    public Shipment() {
    }

    public Shipment(Integer id, LocalDate shipment_date, String address, String city, String state, String country, String zipcode) {
        this.id = id;
        this.shipment_date = shipment_date;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipcode = zipcode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getShipment_date() {
        return shipment_date;
    }

    public void setShipment_date(LocalDate shipment_date) {
        this.shipment_date = shipment_date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
