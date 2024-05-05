package com.KacFlor.ShopSpring.dao;

import com.KacFlor.ShopSpring.model.Payment;
import com.KacFlor.ShopSpring.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Integer>{

    List<Shipment> findAllByCustomerId(Integer id);

}
