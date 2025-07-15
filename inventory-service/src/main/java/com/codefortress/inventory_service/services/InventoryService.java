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
    private final Map<Integer, Integer> stock = new HashMap<>();
     private int requestCount = 11;

    public InventoryService() {
        // Cargar datos simulados
        stock.put(1, 10_000);
        stock.put(2, 50_000);
        stock.put(3, 20_000);
    }
    /**
     * Consulta cuántas unidades hay de un producto.
     */
    public Integer getStock(Integer productId) {
        requestCount++;

        logger.info("Request número {} para producto {}", requestCount, productId);

        // Cada bloque de 100 peticiones, falla de la 0-5 del ciclo
        int cycle = requestCount % 100;

        if (cycle >= 0 && cycle <= 5) {
            logger.warn("Simulando error en ciclo {}, request {}", cycle, requestCount);
            throw new RuntimeException("Error simulado en InventoryService");
        }
        // Simular un retraso de 100 ms
        if (cycle >=6 && cycle <=10){
            try{
                logger.info("Simulando retraso de 1500 ms en ciclo {}, petición {}", cycle, requestCount);
                Thread.sleep(1500);
            }catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Error al simular retraso", e);
            }
        }

        Integer available = stock.get(productId);
        logger.info("Consultando stock de {}: {}", productId, available);
        return available;
    }


    /**
     * Decrementa el stock disponible.
     */
    public void decrementStock(Integer productId, Integer quantity) {
        stock.computeIfPresent(productId, (k, v) -> Math.max(v - quantity, 0));
        logger.info("Stock actualizado de {}: {}", productId, stock.get(productId));
    }
}

