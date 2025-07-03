package com.codefortress.notification_service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
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

    @KafkaListener(topics = "order-created", groupId = "notification-group")
    public void handleOrderCreated(OrderEvent orderEvent) {
        logger.info("Recibido evento de orden creada: {}", orderEvent.getOrderId());
        notificationService.publicEmail(orderEvent);
    }
}
