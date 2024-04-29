package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.controllersRequests.CustomerUpdateRequest;
import com.KacFlor.ShopSpring.model.Customer;
import com.KacFlor.ShopSpring.dao.CustomerRepository;
import com.KacFlor.ShopSpring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService{

    final private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public boolean deleteUserById(Integer customerId){
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if(customerOptional.isPresent()){
            Customer customer = customerOptional.get();
            customerRepository.delete(customer);
            return true;
        }else{
            throw new UsernameNotFoundException("Customer not found");
        }
    }

    public List<Customer> getAll(){
        return customerRepository.findAll();
    }

    public Customer getCurrent() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();

        return customerRepository.findByEmail(email);
    }

    public boolean dataChange(CustomerUpdateRequest customerUpdateRequest) {
        // Pobierz aktualnie zalogowanego użytkownika
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();

        // Znajdź klienta w repozytorium na podstawie adresu e-mail
        Customer customer = customerRepository.findByEmail(email);
        if (customer != null) {
            // Aktualizuj dane klienta na podstawie danych z CustomerUpdateRequest
            customer.setFirstName(customerUpdateRequest.getFirstName());
            customer.setLastName(customerUpdateRequest.getLastName());
            customer.setEmail(customerUpdateRequest.getEmail());
            customer.setAddress(customerUpdateRequest.getAddress());

            // Sprawdzamy, czy numer telefonu nie jest pusty
            if (customerUpdateRequest.getPhoneNumber() != null && !customerUpdateRequest.getPhoneNumber().isEmpty()) {
                // Konwertujemy numer telefonu na typ Number i ustawiamy go w obiekcie Customer
                try {
                    Number phoneNumber = Long.parseLong(customerUpdateRequest.getPhoneNumber());
                    customer.setPhoneNumber(phoneNumber);
                } catch (NumberFormatException e) {
                    // Obsługa błędu, gdy numer telefonu nie jest poprawny
                    System.err.println("Niepoprawny format numeru telefonu: " + customerUpdateRequest.getPhoneNumber());
                }
            }

            // Zapisz zaktualizowanego klienta
            customerRepository.save(customer);

            return true; // Zwróć true, jeśli aktualizacja zakończyła się sukcesem
        } else {
            return false; // Zwróć false, jeśli klient nie został znaleziony
        }
    }

    public boolean deleteCustomer(){
        return true;
    }
}

