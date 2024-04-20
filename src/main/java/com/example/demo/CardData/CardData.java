package com.example.demo.CardData;

import jakarta.persistence.Entity;

import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class CardData {

    private Integer id;
    private String cardNum;

    public CardData() {
    }

    public CardData(Integer id, String cardNum) {
        this.id = id;
        this.cardNum = cardNum;
    }
}
