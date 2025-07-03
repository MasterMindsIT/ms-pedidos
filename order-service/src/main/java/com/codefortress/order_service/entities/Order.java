package com.codefortress.order_service.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Order {
    private String id;
    private String productId;
    private Integer quantity;
    private String status;
}
