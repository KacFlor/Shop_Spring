package com.KacFlor.ShopSpring.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cardData")
@Getter
@Setter
public class CardData extends BaseEntity{

    @Column(name = "cardNum")
    @NotBlank
    private String cardNum;
dsadasfea
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public CardData() {
    }

    public CardData(String cardNum) {
        this.cardNum = cardNum;
    }
}
