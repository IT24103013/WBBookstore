// src/main/java/com/ku_26/webbasedbookstore/repository/OrderRepository.java
package com.ku_26.webbasedbookstore.repository;

import com.ku_26.webbasedbookstore.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper for Order
    private final OrderRowMapper orderRowMapper = new OrderRowMapper();

    private static class OrderRowMapper implements RowMapper<Order> {
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();
            order.setOrderId(rs.getInt("order_id"));
            order.setCustomerId(rs.getInt("customer_id"));
            order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
            order.setTotalAmount(rs.getBigDecimal("total_amount"));
            order.setStatus(rs.getString("status"));
            order.setShippingAddress(rs.getString("shipping_address"));
            order.setPaymentMethod(rs.getString("payment_method"));
            return order;
        }
    }

    // Get all orders
    public List<Order> findAll() {
        String sql = "SELECT * FROM orders ORDER BY order_date DESC";
        return jdbcTemplate.query(sql, orderRowMapper);
    }

    // Get order by ID
    public Optional<Order> findById(int orderId) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        try {
            Order order = jdbcTemplate.queryForObject(sql, orderRowMapper, orderId);
            return Optional.ofNullable(order);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // Get orders by status
    public List<Order> findByStatus(String status) {
        String sql = "SELECT * FROM orders WHERE status = ? ORDER BY order_date DESC";
        return jdbcTemplate.query(sql, orderRowMapper, status);
    }

    // Update order status
    public boolean updateOrderStatus(int orderId, String newStatus) {
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, newStatus, orderId);
        return rowsAffected > 0;
    }

    // Get orders by customer ID
    public List<Order> findByCustomerId(int customerId) {
        String sql = "SELECT * FROM orders WHERE customer_id = ? ORDER BY order_date DESC";
        return jdbcTemplate.query(sql, orderRowMapper, customerId);
    }
}