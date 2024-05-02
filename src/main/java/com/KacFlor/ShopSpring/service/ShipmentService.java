package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.controllersRequests.CustomerUpdateRequest;
import com.KacFlor.ShopSpring.controllersRequests.NewShipment;
import com.KacFlor.ShopSpring.dao.CustomerRepository;
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
import java.util.stream.Collectors;

@Service
public class ShipmentService{

    private final ShipmentRepository shipmentRepository;

    private final CustomerRepository customerRepository;

    @Autowired
    public ShipmentService(ShipmentRepository shipmentRepository, CustomerRepository customerRepository){
        this.shipmentRepository = shipmentRepository;
        this.customerRepository = customerRepository;
    }

    public List<Shipment> getAll(){
        return shipmentRepository.findAll();
    }

    public List<Shipment> getAllByCustomerId(Integer Id) {

        List<Shipment> allShipments = shipmentRepository.findAll();

        List<Shipment> shipmentsForCustomer = allShipments.stream()
                .filter(shipment -> shipment.getCustomer() != null && shipment.getCustomer().getId().equals(Id))
                .collect(Collectors.toList());

        return shipmentsForCustomer;
    }


    public Shipment getById(Long Id) {

        Optional<Shipment> optionalShipment = shipmentRepository.findById(Id);

        return optionalShipment.get();
    }


    public boolean deleteAllByCustomerId(Integer Id){

        List<Shipment> allShipments = shipmentRepository.findAll();

        List<Shipment> shipmentsForCustomer = allShipments.stream()
                .filter(shipment -> shipment.getCustomer() != null && shipment.getCustomer().getId().equals(Id))
                .collect(Collectors.toList());

        shipmentRepository.deleteAll(shipmentsForCustomer);

        return true;
    }

    public boolean deleteById(Long Id){

        Optional<Shipment> optionalShipment = shipmentRepository.findById(Id);

        Shipment shipment = optionalShipment.get();


        shipmentRepository.delete(shipment);
        return true;
    }

    public boolean updateShipment(NewShipment newShipment, Long Id){

        Optional<Shipment> optionalExistingShipment = shipmentRepository.findById(Id);

        Shipment shipment = optionalExistingShipment.get();


        shipment.setShipmentDate(newShipment.getShipmentDate());
        shipment.setAddress(newShipment.getAddress());
        shipment.setCity(newShipment.getCity());
        shipment.setState(newShipment.getState());
        shipment.setCountry(newShipment.getCountry());
        shipment.setZipcode(newShipment.getZipcode());

        shipmentRepository.save(shipment);

        return true;
    }
}
