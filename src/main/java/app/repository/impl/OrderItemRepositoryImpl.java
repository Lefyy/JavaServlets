package app.repository.impl;

import app.config.DatabaseConnection;
import app.model.OrderItem;
import app.repository.OrderItemRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderItemRepositoryImpl implements OrderItemRepository {

    @Override
    public OrderItem save(OrderItem orderItem) {
        String sql = "INSERT INTO order_items (order_id, product_id, quantity, price_at_purchase) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, orderItem.getOrderId());
            pstmt.setInt(2, orderItem.getProductId());
            pstmt.setInt(3, orderItem.getQuantity());
            pstmt.setBigDecimal(4, orderItem.getPriceAtPurchase());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    orderItem.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при добавлении позиции заказа", e);
        }
        return orderItem;
    }

    @Override
    public Optional<OrderItem> findById(Integer id) {
        String sql = "SELECT * FROM order_items WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToOrderItem(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске позиции заказа по ID", e);
        }
        return Optional.empty();
    }

    @Override
    public List<OrderItem> findAll() {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT * FROM order_items";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                items.add(mapResultSetToOrderItem(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении списка позиций заказов", e);
        }
        return items;
    }

    @Override
    public void update(OrderItem orderItem) {
        String sql = "UPDATE order_items SET order_id = ?, product_id = ?, quantity = ?, price_at_purchase = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderItem.getOrderId());
            pstmt.setInt(2, orderItem.getProductId());
            pstmt.setInt(3, orderItem.getQuantity());
            pstmt.setBigDecimal(4, orderItem.getPriceAtPurchase());
            pstmt.setInt(5, orderItem.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении позиции заказа", e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM order_items WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении позиции заказа", e);
        }
    }

    @Override
    public List<OrderItem> findByOrderId(Integer orderId) {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    items.add(mapResultSetToOrderItem(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске позиций заказа", e);
        }
        return items;
    }

    @Override
    public boolean deleteByOrderId(Integer orderId) {
        String sql = "DELETE FROM order_items WHERE order_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            return pstmt.executeUpdate() >= 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении позиций заказа", e);
        }
    }

    private OrderItem mapResultSetToOrderItem(ResultSet rs) throws SQLException {
        return new OrderItem(
                rs.getInt("id"),
                rs.getInt("order_id"),
                rs.getInt("product_id"),
                rs.getInt("quantity"),
                rs.getBigDecimal("price_at_purchase")
        );
    }
}
