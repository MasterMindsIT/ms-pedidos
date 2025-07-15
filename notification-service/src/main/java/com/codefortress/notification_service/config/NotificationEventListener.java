package com.codefortress.notification_service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.codefortress.notification_service.dtos.OrderEvent;
import com.codefortress.notification_service.services.NotificationService;

import io.micrometer.core.instrument.MeterRegistry;

@Service
public class NotificationEventListener {

    private static final Logger logger = LoggerFactory.getLogger(NotificationEventListener.class);
    private final MeterRegistry meterRegistry;
    private final NotificationService notificationService;

    public NotificationEventListener(NotificationService notificationService, MeterRegistry meterRegistry) {
        this.notificationService = notificationService;
        this.meterRegistry = meterRegistry;
    }

    @KafkaListener(
        topics = "order-created",
        groupId = "notification-group",
        containerFactory = "kafkaListenerContainerFactoryManualAck"
    )
    public void handleOrderCreated(OrderEvent orderEvent, Acknowledgment ack) {
        logger.info("Recibido evento de orden creada: {}", orderEvent.getOrderId());
        try {
            notificationService.publicEmail(orderEvent); 
        } catch (Exception e) {
            logger.error("Error procesando evento de orden: {}", orderEvent.getOrderId(), e);
            //meterRegistry.counter("notification.errors", "type", "simulated", "productId", "example") si hay algo que se quiera medir
        } finally {
            ack.acknowledge(); // Siempre confirmar, incluso en error, para evitar el loop 
        }
    }
}

