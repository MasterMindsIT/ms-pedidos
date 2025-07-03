package com.codefortress.inventory_service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.codefortress.inventory_service.dtos.OrderEvent;
import com.codefortress.inventory_service.services.InventoryService;

@Service
public class InventoryEventListener {

    private final InventoryService inventoryService;
    private static final Logger logger = LoggerFactory.getLogger(InventoryEventListener.class);

    public InventoryEventListener(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = "order-created")
    public void handleOrderCreated(OrderEvent orderEvent) {
        logger.info("Recibido evento de orden creada: {}", orderEvent.getOrderId());
        inventoryService.decrementStock(orderEvent.getProductId(), orderEvent.getQuantity());
    }
}
