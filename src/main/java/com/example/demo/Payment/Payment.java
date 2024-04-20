package com.example.demo.Payment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
public class Payment {

    private Integer id;
    private LocalDate paymentDate;
    private String paymentMet;
    private Double amount;

    public Payment() {
    }

    public Payment(Integer id, LocalDate paymentDate, String paymentMet, Double amount) {
        this.id = id;
        this.paymentDate = paymentDate;
        this.paymentMet = paymentMet;
        this.amount = amount;
    }
}
