package com.KacFlor.ShopSpring.dao;

import com.KacFlor.ShopSpring.model.Payment;
import com.KacFlor.ShopSpring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>{

    Payment findByShipmentId(Integer id);

}

