package com.KacFlor.ShopSpring.controller;

import java.util.List;
import java.util.Optional;

import com.KacFlor.ShopSpring.controllersRequests.NewPayment;
import com.KacFlor.ShopSpring.controllersRequests.NewShipment;
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
        path = {"shipments"}
)
public class ShipmentController{

    private final ShipmentService shipmentService;

    @Autowired
    public ShipmentController(ShipmentService shipmentService){
        this.shipmentService = shipmentService;
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @GetMapping
    public ResponseEntity<List<Shipment>> getAllShipments(){
        List<Shipment> shipments = shipmentService.getAllShipments();
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
    public ResponseEntity<Optional<Shipment>> getShipment(@PathVariable("id") Integer id){
        Optional<Shipment> shipment = Optional.ofNullable(shipmentService.getById(id));
        return ResponseEntity.ok(shipment);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @DeleteMapping("/customer/{id}")
    public ResponseEntity<?> deleteAllByCustomerId(@PathVariable("id") Integer id){
        shipmentService.deleteAllByCustomerId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id){
        shipmentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateShipment(@RequestBody NewShipment newShipment, @PathVariable("id") Integer id){
        shipmentService.updateShipment(newShipment, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @PostMapping("/{id}/payment")
    public ResponseEntity<?> addNewPayment(@RequestBody NewPayment newPayment, @PathVariable("id") Integer id){
        shipmentService.addNewPayment(newPayment, id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
