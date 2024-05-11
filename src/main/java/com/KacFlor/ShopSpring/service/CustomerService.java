package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.Exceptions.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.NewCardData;
import com.KacFlor.ShopSpring.controllersRequests.NewCustomer;
import com.KacFlor.ShopSpring.controllersRequests.NewShipment;
import com.KacFlor.ShopSpring.dao.*;
import com.KacFlor.ShopSpring.model.*;
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

    final private CardDataRepository cardDataRepository;

    private final WishlistRepository wishlistRepository;

    private final CartRepository cartRepository;

    private final ReviewRepository reviewRepository;

    @Autowired
    public CustomerService(ReviewRepository reviewRepository, CartRepository cartRepository, WishlistRepository wishlistRepository, CardDataRepository cardDataRepository,CustomerRepository customerRepository, UserRepository userRepository, ShipmentRepository shipmentRepository, OrderRepository orderRepository){
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.shipmentRepository = shipmentRepository;
        this.orderRepository = orderRepository;
        this.cardDataRepository = cardDataRepository;
        this.wishlistRepository = wishlistRepository;
        this.cartRepository = cartRepository;
        this.reviewRepository = reviewRepository;
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

    public boolean createCardData(NewCardData newCardData){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userRepository.findByLogin(username);

        Customer customer = user.getCustomer();

        CardData cardData = new CardData(newCardData.getCardNum(),customer);
        customer.getCardDatas().add(cardData);
        customerRepository.save(customer);

        cardDataRepository.save(cardData);
        return true;
    }

    public Customer getCustomerById(Integer customerId){
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

        if (optionalCustomer.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Customer not found");
        }

        return optionalCustomer.get();

    }

    public boolean deleteCustomerById(Integer customerId){
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        Optional<User> optionalUser = userRepository.findById(customerId);

        if (optionalCustomer.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Customer not found");
        }

        if (optionalUser.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("User not found");
        }

        Customer customer = optionalCustomer.get();
        User user = optionalUser.get();

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

    public boolean updateCustomer(NewCustomer newCustomer){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userRepository.findByLogin(username);

        String name = user.getCustomer().getFirstName();

        Customer customer = customerRepository.findByFirstName(name);

        customer.setFirstName(newCustomer.getFirstName());
        customer.setLastName(newCustomer.getLastName());
        customer.setEmail(newCustomer.getEmail());
        customer.setAddress(newCustomer.getAddress());
        customer.setPhoneNumber(newCustomer.getPhoneNumber());

        customerRepository.save(customer);

        return true;

    }

    public boolean deleteCurrentCustomer(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByLogin(username);
        Customer customer = user.getCustomer();

        customerRepository.delete(customer);
        userRepository.delete(user);

        return true;
    }

}

