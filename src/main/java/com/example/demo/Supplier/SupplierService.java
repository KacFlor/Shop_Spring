package com.example.demo.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    @Autowired
    public SupplierService() {

    }

    public List<Supplier> getSupplier() {return List;}
}
