package com.KacFlor.ShopSpring.dao;

import com.KacFlor.ShopSpring.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>{

    Payment findByShipmentId(Integer id);

    List<Payment> findAllByShipmentId(Integer id);
}

