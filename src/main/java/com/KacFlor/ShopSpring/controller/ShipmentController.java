package com.KacFlor.ShopSpring.controller;

import java.util.List;

import com.KacFlor.ShopSpring.controllersRequests.CustomerUpdateRequest;
import com.KacFlor.ShopSpring.controllersRequests.NewShipment;
import com.KacFlor.ShopSpring.model.Customer;
import com.KacFlor.ShopSpring.model.Role;
import com.KacFlor.ShopSpring.model.Shipment;
import com.KacFlor.ShopSpring.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        path = {"shipment"}
)
public class ShipmentController{

    private final ShipmentService shipmentService;

    @Autowired
    public ShipmentController(ShipmentService shipmentService){
        this.shipmentService = shipmentService;
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @GetMapping("/all")
    public ResponseEntity<List<Shipment>> getAllShipments(){
        List<Shipment> shipments = shipmentService.getAll();
        return ResponseEntity.ok(shipments);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @GetMapping("/customer/{id}")
    public ResponseEntity<List<Shipment>> getCustomerShipments(@PathVariable("id") Integer id){
        List<Shipment> shipments = shipmentService.getAllByCustomerId(id);
        return ResponseEntity.ok(shipments);
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @GetMapping("/{id}")
    public ResponseEntity<Shipment> getShipment(@PathVariable("id") Long id){
        Shipment shipment = shipmentService.getById(id);
        return ResponseEntity.ok(shipment);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @DeleteMapping("/customer/{id}")
    public ResponseEntity deleteAllByCustomerId(@PathVariable("id") Integer id){
        shipmentService.deleteAllByCustomerId(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Long id){
        shipmentService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateShipment(@RequestBody NewShipment newShipment, @PathVariable("id") Long id){
        shipmentService.updateShipment(newShipment,id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
