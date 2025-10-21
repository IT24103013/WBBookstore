// src/main/java/com/ku_26/webbasedbookstore/service/OrderService.java
package com.ku_26.webbasedbookstore.service;

import com.ku_26.webbasedbookstore.model.Order;
import com.ku_26.webbasedbookstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(int orderId) {
        return orderRepository.findById(orderId);
    }

    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    public boolean updateOrderStatus(int orderId, String newStatus) {
        // Validate status
        if (!isValidStatus(newStatus)) {
            throw new IllegalArgumentException("Invalid order status: " + newStatus);
        }

        return orderRepository.updateOrderStatus(orderId, newStatus);
    }

    public List<Order> getOrdersByCustomerId(int customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    private boolean isValidStatus(String status) {
        return List.of("PENDING", "PROCESSING", "OUT_FOR_DELIVERY", "DELIVERED", "CANCELLED")
                .contains(status);
    }
}