package com.codefortress.inventory_service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.codefortress.inventory_service.dtos.OrderEvent;
import com.codefortress.inventory_service.services.InventoryService;

import io.micrometer.core.instrument.MeterRegistry;

@Service
public class InventoryEventListener {

    private final InventoryService inventoryService;
    private final MeterRegistry meterRegistry;
    private static final Logger logger = LoggerFactory.getLogger(InventoryEventListener.class);

    public InventoryEventListener(InventoryService inventoryService, MeterRegistry meterRegistry) {
        this.inventoryService = inventoryService;
        this.meterRegistry = meterRegistry;
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
        } catch (Exception e) {
            logger.error("Error simulado al actualizar inventario para orden {}: {}", orderEvent.getOrderId(), e.getMessage(), e);
           // Métrica de error
            meterRegistry.counter("inventory.errors", "type", "simulated", "productId", String.valueOf(orderEvent.getProductId()))
                     .increment();
        } finally {
            ack.acknowledge(); // Siempre confirmar, incluso en error, para evitar el loop 
        }
    }

}

