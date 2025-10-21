// src/main/java/com/ku_26/webbasedbookstore/model/DeliveryOrder.java
package com.ku_26.webbasedbookstore.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DeliveryOrder {
    private Integer orderId;
    private Integer customerId;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String status;
    private String shippingAddress;
    private String paymentMethod;

    // Customer info for delivery
    private String customerName;
    private String customerPhone;

    // Delivery tracking info
    private Integer trackingId;
    private Integer deliveryStaffId;
    private String deliveryLocation;
    private LocalDateTime estimatedDelivery;
    private LocalDateTime actualDelivery;
    private String deliveryNotes;

    // Constructors
    public DeliveryOrder() {}

    // Getters and Setters
    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    public Integer getTrackingId() { return trackingId; }
    public void setTrackingId(Integer trackingId) { this.trackingId = trackingId; }

    public Integer getDeliveryStaffId() { return deliveryStaffId; }
    public void setDeliveryStaffId(Integer deliveryStaffId) { this.deliveryStaffId = deliveryStaffId; }

    public String getDeliveryLocation() { return deliveryLocation; }
    public void setDeliveryLocation(String deliveryLocation) { this.deliveryLocation = deliveryLocation; }

    public LocalDateTime getEstimatedDelivery() { return estimatedDelivery; }
    public void setEstimatedDelivery(LocalDateTime estimatedDelivery) { this.estimatedDelivery = estimatedDelivery; }

    public LocalDateTime getActualDelivery() { return actualDelivery; }
    public void setActualDelivery(LocalDateTime actualDelivery) { this.actualDelivery = actualDelivery; }

    public String getDeliveryNotes() { return deliveryNotes; }
    public void setDeliveryNotes(String deliveryNotes) { this.deliveryNotes = deliveryNotes; }
}