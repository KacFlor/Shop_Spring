package com.example.demo.Order_item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Order_itemService {

    @Autowired
    public Order_itemService() {

    }

    public List<Order_item> getOrder_item() {return List.of();}
}
