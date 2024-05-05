package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.config.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.CustomerUpdateRequest;
import com.KacFlor.ShopSpring.controllersRequests.NewOrder;
import com.KacFlor.ShopSpring.controllersRequests.NewShipment;
import com.KacFlor.ShopSpring.dao.OrderRepository;
import com.KacFlor.ShopSpring.dao.ShipmentRepository;
import com.KacFlor.ShopSpring.dao.UserRepository;
import com.KacFlor.ShopSpring.model.Customer;
import com.KacFlor.ShopSpring.dao.CustomerRepository;
import com.KacFlor.ShopSpring.model.Order;
import com.KacFlor.ShopSpring.model.Shipment;
import com.KacFlor.ShopSpring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService{

    final private CustomerRepository customerRepository;

    final private UserRepository userRepository;

    final private ShipmentRepository shipmentRepository;

    final private OrderRepository orderRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, UserRepository userRepository, ShipmentRepository shipmentRepository, OrderRepository orderRepository){
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.shipmentRepository = shipmentRepository;
        this.orderRepository = orderRepository;
    }

    public boolean createShipment(NewShipment newShipment){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userRepository.findByLogin(username);

        Customer customer = user.getCustomer();

        Shipment shipment = new Shipment(newShipment.getShipmentDate(), newShipment.getAddress(), newShipment.getCity(), newShipment.getState(), newShipment.getCountry(), newShipment.getZipcode());

        customer.getShipments().add(shipment);
        customerRepository.save(customer);

        shipment.setCustomer(customer);
        shipmentRepository.save(shipment);
        return true;
    }

    public boolean createOrder(NewOrder newOrder, Integer shipmentId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userRepository.findByLogin(username);

        Customer customer = user.getCustomer();

        Optional<Shipment> shipment = shipmentRepository.findById(shipmentId);

        if(customer.getShipments().isEmpty()){
            throw new ExceptionsConfig.ResourceNotFoundException("Shipment not found");
        }
        else{
            Shipment currentShipment = shipment.get();
            Order order = new Order(newOrder.getOrderDate(), newOrder.getTotalPrice());

            customer.getOrders().add(order);
            customerRepository.save(customer);

            order.setCustomer(customer);
            orderRepository.save(order);

            currentShipment.getOrders().add(order);
            shipmentRepository.save(currentShipment);
            return true;
        }
    }

    public Customer getCustomerById(Integer customerId){
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if(customerOptional.isPresent()){
            return customerOptional.get();
        }
        else{
            throw new ExceptionsConfig.ResourceNotFoundException("Customer not found");
        }
    }

    public boolean deleteCustomerById(Integer customerId){
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        Optional<User> userOptional = userRepository.findById(customerId);

        Customer customer = customerOptional.get();
        User user = userOptional.get();
        customerRepository.delete(customer);
        userRepository.delete(user);
        return true;


    }

    public List<Customer> getAll(){
        return customerRepository.findAll();
    }

    public Customer getCurrent(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userRepository.findByLogin(username);

        return user.getCustomer();
    }

    public boolean updateCustomer(CustomerUpdateRequest customerUpdateRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userRepository.findByLogin(username);

        String name = user.getCustomer().getFirstName();

        Customer customer = customerRepository.findByFirstName(name);

        customer.setFirstName(customerUpdateRequest.getFirstName());
        customer.setLastName(customerUpdateRequest.getLastName());
        customer.setEmail(customerUpdateRequest.getEmail());
        customer.setAddress(customerUpdateRequest.getAddress());
        customer.setPhoneNumber(customerUpdateRequest.getPhoneNumber());

        customerRepository.save(customer);

        return true;

    }

    public boolean deleteCurrentCustomer(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userRepository.findByLogin(username);

        String name = user.getCustomer().getFirstName();

        Customer customer = customerRepository.findByFirstName(name);

        customerRepository.delete(customer);
        userRepository.delete(user);

        return true;
    }
}

