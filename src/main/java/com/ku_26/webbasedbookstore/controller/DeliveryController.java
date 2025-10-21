package com.ku_26.webbasedbookstore.controller;

import com.ku_26.webbasedbookstore.model.DeliveryOrder;
import com.ku_26.webbasedbookstore.model.DeliveryStaff;
import com.ku_26.webbasedbookstore.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping("/dashboard")
    public String deliveryDashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("username", username);
        model.addAttribute("role", "DELIVERY_STAFF");

        try {
            // Get delivery staff info
            DeliveryStaff staff = deliveryService.getDeliveryStaffByUsername(username);
            model.addAttribute("deliveryStaff", staff);

            if (staff != null) {
                // Get assigned orders
                List<DeliveryOrder> assignedOrders = deliveryService.getAssignedOrders(staff.getDeliveryStaffId());
                model.addAttribute("assignedOrders", assignedOrders);

                // Get available orders for assignment
                List<DeliveryOrder> availableOrders = deliveryService.getAvailableOrders();
                model.addAttribute("availableOrders", availableOrders);
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error loading dashboard data: " + e.getMessage());
        }

        return "delivery/dashboard";
    }

    @PostMapping("/assign-order")
    public String assignOrder(@RequestParam Integer orderId,
                              Authentication authentication,
                              Model model) {
        try {
            String username = authentication.getName();
            DeliveryStaff staff = deliveryService.getDeliveryStaffByUsername(username);

            if (staff != null) {
                boolean assigned = deliveryService.assignOrderToStaff(orderId, staff.getDeliveryStaffId());
                if (assigned) {
                    model.addAttribute("success", "Order assigned successfully!");
                } else {
                    model.addAttribute("error", "Failed to assign order.");
                }
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error assigning order: " + e.getMessage());
        }

        return "redirect:/delivery/dashboard";
    }

    @PostMapping("/update-status")
    public String updateDeliveryStatus(@RequestParam Integer orderId,
                                       @RequestParam String status,
                                       Model model) {
        try {
            boolean updated = deliveryService.updateOrderStatus(orderId, status);
            if (updated) {
                model.addAttribute("success", "Order status updated successfully!");
            } else {
                model.addAttribute("error", "Failed to update order status.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error updating status: " + e.getMessage());
        }

        return "redirect:/delivery/dashboard";
    }

    // REST API endpoints for AJAX calls
    @GetMapping("/api/orders/assigned")
    @ResponseBody
    public List<DeliveryOrder> getAssignedOrdersApi(Authentication authentication) {
        try {
            DeliveryStaff staff = deliveryService.getDeliveryStaffByUsername(authentication.getName());
            if (staff != null) {
                return deliveryService.getAssignedOrders(staff.getDeliveryStaffId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @GetMapping("/api/orders/available")
    @ResponseBody
    public List<DeliveryOrder> getAvailableOrdersApi() {
        try {
            return deliveryService.getAvailableOrders();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @PostMapping("/api/orders/{orderId}/status")
    @ResponseBody
    public ResponseEntity<?> updateOrderStatusApi(@PathVariable Integer orderId,
                                                  @RequestParam String status) {
        try {
            boolean updated = deliveryService.updateOrderStatus(orderId, status);
            if (updated) {
                return ResponseEntity.ok().body("Status updated successfully");
            } else {
                return ResponseEntity.badRequest().body("Failed to update status");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}