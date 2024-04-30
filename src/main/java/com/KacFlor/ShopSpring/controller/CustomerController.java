package com.KacFlor.ShopSpring.controller;

import java.util.List;
import java.util.Optional;

import com.KacFlor.ShopSpring.controllersRequests.CustomerUpdateRequest;
import com.KacFlor.ShopSpring.model.Customer;
import com.KacFlor.ShopSpring.model.Role;
import com.KacFlor.ShopSpring.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        path = {"customer"}
)
public class CustomerController{

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Customer>> getById(@PathVariable("id") Integer id){
        Optional<Customer> customer = Optional.ofNullable(customerService.getCustomerById(id));
        return ResponseEntity.ok(customer);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Integer id){
        customerService.deleteCustomerById(id);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getCustomersList(){
        List<Customer> customers = customerService.getAll();
        return ResponseEntity.ok(customers);
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @GetMapping("/me")
    public ResponseEntity<Optional<Customer>> getCustomer(){
        Optional<Customer> current = Optional.ofNullable(customerService.getCurrent());
        return ResponseEntity.ok(current);
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @PatchMapping("/me")
    public ResponseEntity<?> dataChange(@RequestBody CustomerUpdateRequest customerUpdateRequest){
        customerService.updateCustomer(customerUpdateRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.USER + "')")
    @DeleteMapping("/me")
    public ResponseEntity deleteCustomer(){
        customerService.deleteCurrentCustomer();
        return new ResponseEntity(HttpStatus.OK);
    }
}
