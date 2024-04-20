package com.example.demo.Payment;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table
public class Payment {
    private Integer id;
    private LocalDate payment_date;
    private String payment_met;
    private Double amount;

    public Payment() {
    }

    public Payment(Integer id, LocalDate payment_date, String payment_met, Double amount) {
        this.id = id;
        this.payment_date = payment_date;
        this.payment_met = payment_met;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(LocalDate payment_date) {
        this.payment_date = payment_date;
    }

    public String getPayment_met() {
        return payment_met;
    }

    public void setPayment_met(String payment_met) {
        this.payment_met = payment_met;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
