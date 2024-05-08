package com.KacFlor.ShopSpring.controller;


import java.util.List;
import java.util.Optional;

import com.KacFlor.ShopSpring.controllersRequests.NewSupplier;
import com.KacFlor.ShopSpring.model.Role;
import com.KacFlor.ShopSpring.model.Supplier;
import com.KacFlor.ShopSpring.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        path = {"suppliers"}
)
public class SupplierController{

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService){
        this.supplierService = supplierService;
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @GetMapping
    public List<Supplier> getAll(){
        return this.supplierService.getAll();
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Supplier>> getById(@PathVariable("id") Integer id){
        Optional<Supplier> supplier = Optional.ofNullable(supplierService.getById(id));
        return ResponseEntity.ok(supplier);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id){
        supplierService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateSupplier(@RequestBody NewSupplier newSupplier, @PathVariable("id") Integer id){
        supplierService.updateSupplier(newSupplier, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @PostMapping("/new")
    public ResponseEntity<?> createNewSupplier(@RequestBody NewSupplier newSupplier){
        supplierService.addNewSupplier(newSupplier);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
