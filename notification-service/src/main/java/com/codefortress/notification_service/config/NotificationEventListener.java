package com.codefortress.notification_service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.codefortress.notification_service.dtos.OrderEvent;
import com.codefortress.notification_service.services.NotificationService;

@Service
public class NotificationEventListener {

    private static final Logger logger = LoggerFactory.getLogger(NotificationEventListener.class);
    private final NotificationService notificationService;

    public NotificationEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
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
            ack.acknowledge(); // Confirmamos a Kafka que procesamos el evento
        } catch (Exception e) {
            logger.error("Error procesando evento de orden: {}", orderEvent.getOrderId(), e);
            // Aquí decides si haces un reintento, envías a DLQ o solo logueas.
            // Si NO llamas a ack.acknowledge(), Kafka reintentará.
        }
    }
}

