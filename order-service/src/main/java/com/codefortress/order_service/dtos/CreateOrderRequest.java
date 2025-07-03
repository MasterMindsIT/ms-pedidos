package com.codefortress.order_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class CreateOrderRequest {
    private String productId;
    private Integer quantity;
}
