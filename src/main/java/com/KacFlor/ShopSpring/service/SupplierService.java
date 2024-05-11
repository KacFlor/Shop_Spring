package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.Exceptions.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.NewSupplier;
import com.KacFlor.ShopSpring.dao.SupplierRepository;
import com.KacFlor.ShopSpring.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService{

    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository){
        this.supplierRepository = supplierRepository;
    }

    public List<Supplier> getAll(){
        return supplierRepository.findAll();
    }

    public Supplier getById(Integer Id){

        Optional<Supplier> optionalSupplier = supplierRepository.findById(Id);

        if (optionalSupplier.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Supplier not found");
        }

        return optionalSupplier.get();

    }

    public boolean deleteById(Integer Id){

        Optional<Supplier> optionalSupplier = supplierRepository.findById(Id);

        if (optionalSupplier.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Supplier not found");
        }

        Supplier supplier = optionalSupplier.get();

        supplierRepository.delete(supplier);

        return true;

    }

    public boolean updateSupplier(NewSupplier newSupplier, Integer Id){
        Optional<Supplier> optionalSupplier = supplierRepository.findById(Id);

        if (optionalSupplier.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Supplier not found");
        }

        Supplier supplier = optionalSupplier.get();
        supplier.setName(newSupplier.getName());

        supplierRepository.save(supplier);

        return true;

    }

    public boolean addNewSupplier(NewSupplier newSupplier){

        Supplier supplier = new Supplier();

        supplier.setName(newSupplier.getName());

        supplierRepository.save(supplier);

        return true;

    }
}
