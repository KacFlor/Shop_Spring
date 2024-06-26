package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.Exceptions.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.NewOrder;
import com.KacFlor.ShopSpring.controllersRequests.NewPayment;
import com.KacFlor.ShopSpring.controllersRequests.NewShipment;
import com.KacFlor.ShopSpring.dao.CustomerRepository;
import com.KacFlor.ShopSpring.dao.OrderRepository;
import com.KacFlor.ShopSpring.dao.PaymentRepository;
import com.KacFlor.ShopSpring.dao.ShipmentRepository;
import com.KacFlor.ShopSpring.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipmentService{

    private final ShipmentRepository shipmentRepository;

    private final PaymentRepository paymentRepository;

    private final CustomerRepository customerRepository;

    private final OrderRepository orderRepository;

    @Autowired
    public ShipmentService(OrderRepository orderRepository, ShipmentRepository shipmentRepository, PaymentRepository paymentRepository, CustomerRepository customerRepository){
        this.shipmentRepository = shipmentRepository;
        this.paymentRepository = paymentRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    public List<Shipment> getAllShipments(){
        return shipmentRepository.findAll();
    }

    public List<Order> getAllShipmentOrders(Integer Id){
        Optional<Shipment> optionalShipment = shipmentRepository.findById(Id);

        if (optionalShipment.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Shipment not found");
        }

        Shipment shipment = optionalShipment.get();
        return shipment.getOrders();

    }

    public boolean createOrder(NewOrder newOrder, Integer shipmentId){

        Optional<Shipment> optionalShipment = shipmentRepository.findById(shipmentId);

        if (optionalShipment.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Shipment not found");
        }

        Shipment shipment = optionalShipment.get();
        Order order = new Order(newOrder.getOrderDate(), newOrder.getTotalPrice());
        order.setShipment(shipment);
        orderRepository.save(order);

        shipment.getOrders().add(order);
        shipmentRepository.save(shipment);

        return true;

    }

    public List<Shipment> getAllByCustomerId(Integer Id){

        Optional<Customer> optionalCustomer = customerRepository.findById(Id);

        if (optionalCustomer.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Customer not found");
        }

        return shipmentRepository.findAllByCustomerId(Id);

    }

    public Shipment getById(Integer Id){

        Optional<Shipment> optionalShipment = shipmentRepository.findById(Id);

        if (optionalShipment.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Shipment not found");
        }

        return optionalShipment.get();

    }

    public boolean deleteAllByCustomerId(Integer Id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(Id);

        if (optionalCustomer.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Customer not found");
        }
        Shipment shipment = shipmentRepository.findByCustomerId(Id);

        shipmentRepository.delete(shipment);

        return true;

    }

    public boolean deleteById(Integer Id) {
        Optional<Shipment> optionalShipment = shipmentRepository.findById(Id);

        if (optionalShipment.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Shipment not found");
        }

        Shipment shipment = optionalShipment.get();

        shipmentRepository.deleteById(Id);
        return true;

    }

    public boolean updateShipment(NewShipment newShipment, Integer Id){

        Optional<Shipment> optionalShipment = shipmentRepository.findById(Id);

        if (optionalShipment.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Shipment not found");
        }

        Shipment shipment = optionalShipment.get();

        shipment.setShipmentDate(newShipment.getShipmentDate());
        shipment.setAddress(newShipment.getAddress());
        shipment.setCity(newShipment.getCity());
        shipment.setState(newShipment.getState());
        shipment.setCountry(newShipment.getCountry());
        shipment.setZipcode(newShipment.getZipcode());

        shipmentRepository.save(shipment);

        return true;

    }

    public boolean addNewPayment(NewPayment newPayment, Integer id){

        Optional<Shipment> optionalShipment = shipmentRepository.findById(id);

        if (optionalShipment.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Shipment not found");
        }

        Shipment shipment = optionalShipment.get();
        if(shipment.getPayment() != null){
            throw new UsernameNotFoundException("Payment exist already");
        }

        Payment payment = new Payment(newPayment.getPaymentDate(), newPayment.getPaymentMet(), newPayment.getAmount());
        payment.setShipment(shipment);
        paymentRepository.save(payment);

        shipment.setPayment(payment);
        shipmentRepository.save(shipment);

        return true;
    }


}

