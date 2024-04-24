package com.KacFlor.ShopSpring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Supplier extends BaseEntity{

    @Column(name = "name")
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "supplier")
    private List<Product> products;

    public Supplier(){
    }

    public Supplier(String name){
        this.name = name;
    }
}
