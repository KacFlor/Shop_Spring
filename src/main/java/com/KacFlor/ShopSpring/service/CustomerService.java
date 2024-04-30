package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.controllersRequests.CustomerUpdateRequest;
import com.KacFlor.ShopSpring.dao.UserRepository;
import com.KacFlor.ShopSpring.model.Customer;
import com.KacFlor.ShopSpring.dao.CustomerRepository;
import com.KacFlor.ShopSpring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService{

    final private CustomerRepository customerRepository;
    final private UserRepository userRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, UserRepository userRepository){
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    public boolean deleteCustomerById(Integer customerId){
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        Optional<User> userOptional = userRepository.findById(customerId);
        if(customerOptional.isPresent()){
            Customer customer = customerOptional.get();
            User user = userOptional.get();
            customerRepository.delete(customer);
            userRepository.delete(user);
            return true;
        }else{
            throw new UsernameNotFoundException("Customer not found");
        }
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

    public boolean dataChange(CustomerUpdateRequest customerUpdateRequest) {
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

    public boolean deleteCustomer(){
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

