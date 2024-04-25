package com.KacFlor.ShopSpring.controller;


import java.util.List;

import com.KacFlor.ShopSpring.model.Supplier;
import com.KacFlor.ShopSpring.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        path = {"supplier"}
)
public class SupplierController{

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService){
        this.supplierService = supplierService;
    }

    @GetMapping
    public List<Supplier> getSupplier(){
        return this.supplierService.getSupplier();
    }
}
