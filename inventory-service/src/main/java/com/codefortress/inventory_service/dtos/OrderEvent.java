package com.codefortress.inventory_service.dtos;

public class OrderEvent {
    private String orderId;
    private String productId;
    private Integer quantity;
    
    public OrderEvent(String orderId, String productId, Integer quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }
    public String getOrderId() {
        return orderId;
    }
    public String getProductId() {
        return productId;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
}
