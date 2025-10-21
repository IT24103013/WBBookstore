// src/main/java/com/ku_26/webbasedbookstore/controller/OrderController.java
package com.ku_26.webbasedbookstore.controller;

import com.ku_26.webbasedbookstore.model.Order;
import com.ku_26.webbasedbookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Dashboard - Show all orders
    @GetMapping("/dashboard")
    public String orderDashboard(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "order-dashboard"; // This will be your Thymeleaf template
    }

    // Get orders by status
    @GetMapping("/status/{status}")
    public String getOrdersByStatus(@PathVariable String status, Model model) {
        List<Order> orders = orderService.getOrdersByStatus(status);
        model.addAttribute("orders", orders);
        model.addAttribute("currentStatus", status);
        return "order-dashboard";
    }

    // Update order status - REST endpoint
    @PostMapping("/{orderId}/status")
    @ResponseBody
    public ResponseEntity<?> updateOrderStatus(@PathVariable int orderId,
                                               @RequestParam String newStatus) {
        try {
            boolean updated = orderService.updateOrderStatus(orderId, newStatus);
            if (updated) {
                return ResponseEntity.ok().body("Order status updated successfully");
            } else {
                return ResponseEntity.badRequest().body("Order not found");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get order details
    @GetMapping("/{orderId}")
    public String getOrderDetails(@PathVariable int orderId, Model model) {
        Optional<Order> order = orderService.getOrderById(orderId);
        if (order.isPresent()) {
            model.addAttribute("order", order.get());
            return "order-details";
        } else {
            model.addAttribute("error", "Order not found");
            return "order-dashboard";
        }
    }

    // REST endpoint to get all orders (for AJAX calls)
    @GetMapping("/api/all")
    @ResponseBody
    public List<Order> getAllOrdersApi() {
        return orderService.getAllOrders();
    }
}