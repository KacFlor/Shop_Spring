package com.KacFlor.ShopSpring.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "cardData")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardData extends BaseEntity{

    @Column(name = "cardNum")
    @NotBlank
    private String cardNum;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;


}
