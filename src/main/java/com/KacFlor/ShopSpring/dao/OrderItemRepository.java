package com.KacFlor.ShopSpring.dao;

import com.KacFlor.ShopSpring.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
