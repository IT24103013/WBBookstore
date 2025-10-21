package com.ku_26.webbasedbookstore.model;

public class DeliveryStaff {
    private Integer deliveryStaffId;
    private Integer userId;
    private String vehicleType;
    private String deliveryZone;
    private Integer deliveriesCompleted = 0;

    // Reference to User object (for joined data)
    private User user;

    public DeliveryStaff() {}

    public DeliveryStaff(Integer userId, String vehicleType, String deliveryZone) {
        this.userId = userId;
        this.vehicleType = vehicleType;
        this.deliveryZone = deliveryZone;
    }

    // Getters and Setters
    public Integer getDeliveryStaffId() { return deliveryStaffId; }
    public void setDeliveryStaffId(Integer deliveryStaffId) { this.deliveryStaffId = deliveryStaffId; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public String getDeliveryZone() { return deliveryZone; }
    public void setDeliveryZone(String deliveryZone) { this.deliveryZone = deliveryZone; }

    public Integer getDeliveriesCompleted() { return deliveriesCompleted; }
    public void setDeliveriesCompleted(Integer deliveriesCompleted) { this.deliveriesCompleted = deliveriesCompleted; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}