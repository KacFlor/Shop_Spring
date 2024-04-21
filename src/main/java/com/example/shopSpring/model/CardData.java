package com.example.shopSpring.model;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cardData")
@Getter
@Setter
public class CardData {

    @Id
    private Integer id;

    private String cardNum;

    public CardData() {
    }

    public CardData(Integer id, String cardNum) {
        this.id = id;
        this.cardNum = cardNum;
    }
}
