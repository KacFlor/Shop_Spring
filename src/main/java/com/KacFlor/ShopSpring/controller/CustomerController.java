package com.KacFlor.ShopSpring.controller;

import java.util.List;
import java.util.Optional;

import com.KacFlor.ShopSpring.controllersRequests.NewCardData;
import com.KacFlor.ShopSpring.controllersRequests.NewCustomer;
import com.KacFlor.ShopSpring.controllersRequests.NewShipment;
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
        path = {"customers"}
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
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id){
        customerService.deleteCustomerById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @GetMapping
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
    @PostMapping("/me/shipment")
    public ResponseEntity<?> createNewShipment(@RequestBody NewShipment newShipment){
        customerService.createShipment(newShipment);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @PostMapping("/me/card-data")
    public ResponseEntity<?> createCardData(@RequestBody NewCardData newCardData){
        customerService.createCardData(newCardData);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @PatchMapping("/me")
    public ResponseEntity<?> dataChange(@RequestBody NewCustomer newCustomer){
        customerService.updateCustomer(newCustomer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @DeleteMapping("/me")
    public ResponseEntity<?> deleteCustomer(){
        customerService.deleteCurrentCustomer();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
