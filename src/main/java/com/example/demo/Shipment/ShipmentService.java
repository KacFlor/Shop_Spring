package com.example.demo.Shipment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentService {

    @Autowired
    public ShipmentService() {

    }

    public List<Shipment> getShipment() {return List.of();}
}
