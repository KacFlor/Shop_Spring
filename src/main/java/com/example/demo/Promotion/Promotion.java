package com.example.demo.Promotion;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
public class Promotion {

    private Integer id;
    private  String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private  Double discount;

    public Promotion() {
    }

    public Promotion(Integer id, String name, String description, LocalDate startDate, LocalDate endDate, Double discount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discount = discount;
    }
}
