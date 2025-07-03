package com.codefortress.order_service.services;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.codefortress.order_service.config.InventoryClient;
import com.codefortress.order_service.config.OrderEventProducer;
import com.codefortress.order_service.entities.Order;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final InventoryClient inventoryClient;
    private final OrderEventProducer orderEventProducer;
    
      public Order createOrder(String productId, Integer quantity) {
        logger.info("Verificando stock para producto: {} cantidad solicitada: {}", productId, quantity);

        Integer availableStock = inventoryClient.getStock(productId);

        logger.info("Stock disponible para producto {}: {}", productId, availableStock);

        if (availableStock == null || availableStock < quantity) {
            throw new IllegalStateException("No hay suficiente stock disponible.");
        }

        Order newOrder = new Order(
            UUID.randomUUID().toString(),
            productId,
            quantity,
            "CREATED"
        );

        // Enviar evento Kafka
        orderEventProducer.sendOrderCreatedEvent(newOrder.getId());

        logger.info("Orden creada y evento publicado en Kafka: {}", newOrder.getId());

        return newOrder;
    }

}

