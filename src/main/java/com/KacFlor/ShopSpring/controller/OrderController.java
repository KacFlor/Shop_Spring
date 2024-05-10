package com.KacFlor.ShopSpring.controller;

import java.util.List;
import java.util.Optional;

import com.KacFlor.ShopSpring.controllersRequests.NewOrder;
import com.KacFlor.ShopSpring.model.Order;
import com.KacFlor.ShopSpring.model.OrderItem;
import com.KacFlor.ShopSpring.model.Role;
import com.KacFlor.ShopSpring.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        path = {"orders"}
)
public class OrderController{

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @GetMapping
    public List<Order> getAll(){
        return this.orderService.getAllOrders();
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Order>> getById(@PathVariable("id") Integer id){
        Optional<Order> order = Optional.ofNullable(orderService.getById(id));
        return ResponseEntity.ok(order);
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @GetMapping("/{id}/order-items")
    public List<OrderItem> getAllOrderItemsById(@PathVariable("id") Integer id){
        return this.orderService.getAllOrderItems(id);
    }


    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateShipment(@RequestBody NewOrder newOrder, @PathVariable("id") Integer id){
        orderService.updateOrder(newOrder, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id){
        orderService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
