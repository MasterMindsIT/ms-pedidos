package com.codefortress.order_service.config;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.codefortress.order_service.dtos.OrderEvent;

@Service
public class OrderEventProducer {
     private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderEventProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderCreatedEvent(OrderEvent event) {
        kafkaTemplate.send("order-created", event);
    }
}
