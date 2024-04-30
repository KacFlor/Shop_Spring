package com.KacFlor.ShopSpring.controller;

import java.util.List;

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
        List<Shipment> shipments = shipmentService.getAllByCustomerId();
        return ResponseEntity.ok(shipments);
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @GetMapping("/customer/date/{id}")
    public ResponseEntity<List<Shipment>> getCustomerShipmentsDate(@PathVariable("id") Integer id){
        List<Shipment> shipments = shipmentService.getAllDateByCustomerId();
        return ResponseEntity.ok(shipments);;
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @GetMapping("/{id}")
    public ResponseEntity<List<Shipment>> getShipment(@PathVariable("id") Integer id){
        List<Shipment> shipment = shipmentService.getById();
        return ResponseEntity.ok(shipment);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteAllByCustomerId(){
        shipmentService.deleteAllByCustomerId();
        return new ResponseEntity(HttpStatus.OK);
    }

}
