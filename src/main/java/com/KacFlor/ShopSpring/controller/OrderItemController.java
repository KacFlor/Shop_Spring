package com.KacFlor.ShopSpring.controller;

import java.util.List;
import java.util.Optional;

import com.KacFlor.ShopSpring.model.CardData;
import com.KacFlor.ShopSpring.model.OrderItem;
import com.KacFlor.ShopSpring.model.Role;
import com.KacFlor.ShopSpring.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        path = {"order-items"}
)
public class OrderItemController{

    private final OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService){
        this.orderItemService = orderItemService;
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @GetMapping
    public ResponseEntity<List<OrderItem>> getAll(){
        List<OrderItem> orderItems = orderItemService.getAll();
        return ResponseEntity.ok(orderItems);
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<OrderItem>> getById(@PathVariable("id") Integer id){
        Optional<OrderItem> orderItem = Optional.ofNullable(orderItemService.getOrderItemById(id));
        return ResponseEntity.ok(orderItem);
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id){
        orderItemService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
