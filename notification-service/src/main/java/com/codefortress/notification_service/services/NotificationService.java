package com.codefortress.notification_service.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.codefortress.notification_service.dtos.OrderEvent;

@Service
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    public void publicEmail(OrderEvent orderEvent) {
        // Aquí se implementaría la lógica para enviar un email de confirmación
        // Por ejemplo, podrías usar un servicio de correo electrónico como JavaMailSender 
        logger.info("Enviando email de confirmación para la orden: {}, con el producto {}, cantidad {} y status {}", orderEvent.getOrderId(), orderEvent.getProductId(), orderEvent.getQuantity(), orderEvent.getStatus());

    }

}
