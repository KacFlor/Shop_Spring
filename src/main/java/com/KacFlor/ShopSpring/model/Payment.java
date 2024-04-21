package com.KacFlor.ShopSpring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
public class Payment extends BaseEntity{

    @Column(name = "paymentDate")
    private LocalDate paymentDate;

    @Column(name = "paymentMet")
    @NotBlank
    private String paymentMet;

    @Column(name = "amount")
    private Double amount;

    @OneToOne(mappedBy = "payment", fetch = FetchType.EAGER)
    private Shipment shipment;

    public Payment() {
    }

    public Payment(LocalDate paymentDate, String paymentMet, Double amount) {
        this.paymentDate = paymentDate;
        this.paymentMet = paymentMet;
        this.amount = amount;
    }
}
