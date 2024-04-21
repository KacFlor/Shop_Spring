package com.example.shopSpring.service;

import com.example.shopSpring.model.Shipment;
import com.example.shopSpring.dao.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    @Autowired
    public ShipmentService(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public List<Shipment> getShipment() {
        return shipmentRepository.findAll();
    }
}
