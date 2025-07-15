package com.codefortress.order_service.services;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.codefortress.order_service.config.InventoryClient;
import com.codefortress.order_service.config.OrderEventProducer;
import com.codefortress.order_service.dtos.OrderEvent;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final InventoryClient inventoryClient;
    private final OrderEventProducer orderEventProducer;

    

    @CircuitBreaker(name = "inventoryServiceCB", fallbackMethod = "fallbackGetStock")
    @Retry(name = "inventoryServiceCB")
    public OrderEvent createOrder(Integer productId, Integer quantity) {
        logger.info("Verificando stock para producto: {} cantidad solicitada: {}", productId, quantity);

        try {
            Integer availableStock = inventoryClient.getStock(productId);
            logger.info("Stock disponible para producto {}: {}", productId, availableStock);

            if (availableStock == null || availableStock < quantity) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No hay suficiente stock disponible.");
            }

            OrderEvent newOrder = new OrderEvent(
                    UUID.randomUUID().toString(),
                    productId,
                    quantity
            );

            orderEventProducer.sendOrderCreatedEvent(newOrder);

            logger.info("Orden creada y evento publicado en Kafka: {}", newOrder.getOrderId());
            return newOrder;

        } catch (FeignException.NotFound ex) {
            logger.warn("Producto no encontrado en inventario: {}", productId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no disponible en inventario");
        }
        // Otros FeignException NO los capturas aquí → los maneja @Retry + CircuitBreaker
    }




    /**
     * Fallback method ejecutado si falla el Circuit Breaker o los retries.
     */
    public OrderEvent fallbackGetStock(Integer productId, Integer quantity, Throwable t) {
        logger.error("Circuit Breaker activado al consultar inventario: {}", t.getMessage(), t);
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Inventario temporalmente no disponible");
    }

}