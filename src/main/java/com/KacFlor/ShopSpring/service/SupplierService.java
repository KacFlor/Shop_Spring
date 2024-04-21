package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.dao.SupplierRepository;
import com.KacFlor.ShopSpring.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<Supplier> getSupplier() {
        return supplierRepository.findAll();
    }
}
