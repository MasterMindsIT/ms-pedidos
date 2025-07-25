package com.codefortress.order_service.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codefortress.order_service.dtos.CreateOrderRequest;
import com.codefortress.order_service.dtos.OrderEvent;
import com.codefortress.order_service.services.OrderService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    public ResponseEntity<OrderEvent> create(@RequestBody CreateOrderRequest request) {
        OrderEvent created = orderService.createOrder(request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(created);
    }
}

