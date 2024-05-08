package com.KacFlor.ShopSpring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Promotion extends BaseEntity{

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "description")
    @NotBlank
    private String description;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    @Column(name = "discount")
    private Double discount;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonBackReference
    private List<Product> products;

    public Promotion(){
    }

    public Promotion(String name, String description, LocalDate startDate, LocalDate endDate, Double discount){
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discount = discount;
    }
}
