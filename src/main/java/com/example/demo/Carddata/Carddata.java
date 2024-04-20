package com.example.demo.Carddata;

import jakarta.persistence.Entity;

import jakarta.persistence.Table;

@Entity
@Table
public class Carddata {
    private Integer id;
    private Double Card_num;

    public Carddata() {
    }

    public Carddata(Integer id, Double card_num) {
        this.id = id;
        Card_num = card_num;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getCard_num() {
        return Card_num;
    }

    public void setCard_num(Double card_num) {
        Card_num = card_num;
    }
}
