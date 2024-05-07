package com.KacFlor.ShopSpring.dao;

import com.KacFlor.ShopSpring.model.Order;
import com.KacFlor.ShopSpring.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

    List<Order> findAllByShipmentId(Integer id);

}
