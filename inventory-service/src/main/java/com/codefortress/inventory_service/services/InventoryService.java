package com.codefortress.inventory_service.services;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class InventoryService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);

    // Mapa de productos con stock mutable
    private final Map<String, Integer> stock = new HashMap<>();

    public InventoryService() {
        // Cargar datos simulados
        stock.put("product-1", 10_000);
        stock.put("product-2", 50_000);
        stock.put("product-3", 20_000);
    }
    /**
     * Consulta cuántas unidades hay de un producto.
     */
    public Integer getStock(String productId) {
        Integer available = stock.get(productId);
        logger.info("Consultando stock de {}: {}", productId, available);
        return available;
    }

    /**
     * Decrementa el stock disponible.
     */
    public void decrementStock(String productId, Integer quantity) {
        stock.computeIfPresent(productId, (k, v) -> Math.max(v - quantity, 0));
        logger.info("Stock actualizado de {}: {}", productId, stock.get(productId));
    }
}

