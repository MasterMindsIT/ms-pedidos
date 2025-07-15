package com.codefortress.inventory_service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
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

    @KafkaListener(
        topics = "order-created",
        groupId = "inventory-group",
        containerFactory = "kafkaListenerContainerFactoryManualAck"
    )
    public void handleOrderCreated(OrderEvent orderEvent, Acknowledgment ack) {
        logger.info("Recibido evento de orden creada: {}", orderEvent.getOrderId());

        try {
            inventoryService.decrementStock(orderEvent.getProductId(), orderEvent.getQuantity());
            ack.acknowledge(); // Confirmas a Kafka que ya procesaste el evento
        } catch (Exception e) {
            logger.error("Error actualizando inventario para orden {}: {}", orderEvent.getOrderId(), e.getMessage(), e);
            // Si no llamas a ack.acknowledge() Kafka reintentará
            // Puedes enviar a un DLQ o solo loguear dependiendo de la estrategia
        }
    }
}

