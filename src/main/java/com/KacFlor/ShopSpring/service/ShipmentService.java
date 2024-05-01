package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.controllersRequests.CustomerUpdateRequest;
import com.KacFlor.ShopSpring.controllersRequests.NewShipment;
import com.KacFlor.ShopSpring.dao.ShipmentRepository;
import com.KacFlor.ShopSpring.model.Customer;
import com.KacFlor.ShopSpring.model.Shipment;
import com.KacFlor.ShopSpring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipmentService{

    private final ShipmentRepository shipmentRepository;

    @Autowired
    public ShipmentService(ShipmentRepository shipmentRepository){
        this.shipmentRepository = shipmentRepository;
    }

    public List<Shipment> getAll(){
        return shipmentRepository.findAll();
    }

    public List<Shipment> getAllByCustomerId(Integer Id) {
        return null;
    }

    public Shipment getById(Integer Id) {
        return null;
    }

    public boolean deleteAllByCustomerId(Integer Id){
        return true;
    }

    public boolean deleteById(Integer Id){
        return true;
    }

    public boolean updateShipment(NewShipment newShipment){
        return true;
    }
}
