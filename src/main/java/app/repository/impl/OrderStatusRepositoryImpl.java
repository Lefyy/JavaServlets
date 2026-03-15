package app.repository.impl;

import app.config.DatabaseConnection;
import app.model.OrderStatus;
import app.repository.OrderStatusRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderStatusRepositoryImpl implements OrderStatusRepository {

    @Override
    public OrderStatus save(OrderStatus orderStatus) {
        String sql = "INSERT INTO order_statuses (status_name) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, orderStatus.getName());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    orderStatus.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении статуса заказа", e);
        }
        return orderStatus;
    }

    @Override
    public Optional<OrderStatus> findById(Integer id) {
        String sql = "SELECT * FROM order_statuses WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToOrderStatus(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске статуса заказа по ID", e);
        }
        return Optional.empty();
    }

    @Override
    public List<OrderStatus> findAll() {
        List<OrderStatus> statuses = new ArrayList<>();
        String sql = "SELECT * FROM order_statuses";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                statuses.add(mapResultSetToOrderStatus(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении списка статусов заказов", e);
        }
        return statuses;
    }

    @Override
    public void update(OrderStatus orderStatus) {
        String sql = "UPDATE order_statuses SET status_name = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, orderStatus.getName());
            pstmt.setInt(2, orderStatus.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении статуса заказа", e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM order_statuses WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении статуса заказа", e);
        }
    }

    private OrderStatus mapResultSetToOrderStatus(ResultSet rs) throws SQLException {
        return new OrderStatus(rs.getInt("id"), rs.getString("status_name"));
    }
}
