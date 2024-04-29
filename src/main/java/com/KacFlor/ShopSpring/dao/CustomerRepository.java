package com.KacFlor.ShopSpring.dao;

import com.KacFlor.ShopSpring.model.Customer;
import com.KacFlor.ShopSpring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

    Customer findByEmail(String email);

}
