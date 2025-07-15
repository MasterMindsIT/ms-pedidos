package com.codefortress.inventory_service.dtos;

public class OrderEvent {
    private String orderId;
    private Integer productId;
    private Integer quantity;
    
    public OrderEvent(String orderId, Integer productId, Integer quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }
    public String getOrderId() {
        return orderId;
    }
    public Integer getProductId() {
        return productId;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
}
