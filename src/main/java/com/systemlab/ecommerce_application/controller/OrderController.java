package com.systemlab.ecommerce_application.controller;

import com.systemlab.ecommerce_application.dto.OrderRequest;
import com.systemlab.ecommerce_application.dto.OrderResponse;
import com.systemlab.ecommerce_application.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // @GetMapping
    // public ResponseEntity<List<OrderResponse>> getAllOrders() {
    // var orders = orderService.findAllOrders();
    // return new ResponseEntity<>(orders, HttpStatus.OK);
    // }

    // @GetMapping("/{id}")
    // public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
    // var order = orderService.findById(id);
    // return new ResponseEntity<>(order, HttpStatus.OK);
    // }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestHeader("X-User-Id") String userId) {
        var createdOrder = orderService.createOrder(userId);
        return createdOrder.map(order -> new ResponseEntity<>(order, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<OrderResponse> updateOrder(@PathVariable Long id,
    // @RequestBody OrderRequest orderRequest) {
    // var updatedOrder = orderService.updateOrder(id, orderRequest);
    // return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
    // orderService.deleteOrder(id);
    // return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    // }
}