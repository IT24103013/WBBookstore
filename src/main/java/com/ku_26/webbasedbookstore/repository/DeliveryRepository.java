package com.ku_26.webbasedbookstore.repository;

import com.ku_26.webbasedbookstore.model.DeliveryOrder;
import com.ku_26.webbasedbookstore.model.DeliveryStaff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DeliveryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Get delivery staff by username
    public DeliveryStaff findDeliveryStaffByUsername(String username) {
        String sql = """
            SELECT ds.*, u.first_name, u.last_name 
            FROM delivery_staff ds 
            JOIN users u ON ds.user_id = u.user_id 
            WHERE u.username = ?
            """;
        try {
            return jdbcTemplate.queryForObject(sql, new DeliveryStaffRowMapper(), username);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get assigned orders for delivery staff
    public List<DeliveryOrder> getAssignedOrders(Integer deliveryStaffId) {
        String sql = """
            SELECT o.order_id, o.customer_id, o.order_date, o.total_amount, o.status, 
                   o.shipping_address, o.payment_method,
                   CONCAT(u.first_name, ' ', u.last_name) as customer_name,
                   u.phone as customer_phone
            FROM orders o
            JOIN customers c ON o.customer_id = c.customer_id
            JOIN users u ON c.user_id = u.user_id
            WHERE o.status IN ('PROCESSING', 'OUT_FOR_DELIVERY')
            ORDER BY o.order_date DESC
            """;
        return jdbcTemplate.query(sql, new DeliveryOrderRowMapper());
    }

    // Get available orders for assignment
    public List<DeliveryOrder> getAvailableOrders() {
        String sql = """
            SELECT o.order_id, o.customer_id, o.order_date, o.total_amount, o.status, 
                   o.shipping_address, o.payment_method,
                   CONCAT(u.first_name, ' ', u.last_name) as customer_name,
                   u.phone as customer_phone
            FROM orders o
            JOIN customers c ON o.customer_id = c.customer_id
            JOIN users u ON c.user_id = u.user_id
            WHERE o.status = 'PENDING'
            ORDER BY o.order_date ASC
            """;
        return jdbcTemplate.query(sql, new DeliveryOrderRowMapper());
    }

    // Assign order to delivery staff
    public boolean assignOrderToStaff(Integer orderId, Integer deliveryStaffId) {
        String sql = "UPDATE orders SET status = 'PROCESSING' WHERE order_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, orderId);
        return rowsAffected > 0;
    }

    // Update order status
    public boolean updateOrderStatus(Integer orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, status, orderId);
        return rowsAffected > 0;
    }

    // Row Mappers
    private static class DeliveryStaffRowMapper implements RowMapper<DeliveryStaff> {
        @Override
        public DeliveryStaff mapRow(ResultSet rs, int rowNum) throws SQLException {
            DeliveryStaff staff = new DeliveryStaff();
            staff.setDeliveryStaffId(rs.getInt("delivery_staff_id"));
            staff.setUserId(rs.getInt("user_id"));
            staff.setVehicleType(rs.getString("vehicle_type"));
            staff.setDeliveryZone(rs.getString("delivery_zone"));
            staff.setDeliveriesCompleted(rs.getInt("deliveries_completed"));
            return staff;
        }
    }

    private static class DeliveryOrderRowMapper implements RowMapper<DeliveryOrder> {
        @Override
        public DeliveryOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
            DeliveryOrder order = new DeliveryOrder();
            order.setOrderId(rs.getInt("order_id"));
            order.setCustomerId(rs.getInt("customer_id"));
            if (rs.getTimestamp("order_date") != null) {
                order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
            }
            order.setTotalAmount(rs.getBigDecimal("total_amount"));
            order.setStatus(rs.getString("status"));
            order.setShippingAddress(rs.getString("shipping_address"));
            order.setPaymentMethod(rs.getString("payment_method"));
            order.setCustomerName(rs.getString("customer_name"));
            order.setCustomerPhone(rs.getString("customer_phone"));
            return order;
        }
    }
}