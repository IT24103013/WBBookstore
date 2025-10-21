package com.ku_26.webbasedbookstore.service;

import com.ku_26.webbasedbookstore.model.DeliveryOrder;
import com.ku_26.webbasedbookstore.model.DeliveryStaff;
import com.ku_26.webbasedbookstore.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    public DeliveryStaff getDeliveryStaffByUsername(String username) {
        return deliveryRepository.findDeliveryStaffByUsername(username);
    }

    public List<DeliveryOrder> getAssignedOrders(Integer deliveryStaffId) {
        return deliveryRepository.getAssignedOrders(deliveryStaffId);
    }

    public List<DeliveryOrder> getAvailableOrders() {
        return deliveryRepository.getAvailableOrders();
    }

    public boolean assignOrderToStaff(Integer orderId, Integer deliveryStaffId) {
        return deliveryRepository.assignOrderToStaff(orderId, deliveryStaffId);
    }

    public boolean updateOrderStatus(Integer orderId, String status) {
        return deliveryRepository.updateOrderStatus(orderId, status);
    }
}