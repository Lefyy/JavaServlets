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
        String sql = "INSERT INTO order_statuses (name) VALUES (?)";
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
        String sql = "UPDATE order_statuses SET name = ? WHERE id = ?";
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

    @Override
    public List<OrderStatus> findForAdmin(String query, int limit, int offset) {
        List<OrderStatus> statuses = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM order_statuses");
        boolean hasQuery = query != null && !query.isBlank();
        boolean numericQuery = hasQuery && query.trim().matches("\\d+");
        if (hasQuery) {
            sql.append(" WHERE ");
            if (numericQuery) {
                sql.append("id = ? OR name ILIKE ?");
            } else {
                sql.append("name ILIKE ?");
            }
        }
        sql.append(" ORDER BY id ASC LIMIT ? OFFSET ?");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (hasQuery) {
                if (numericQuery) {
                    pstmt.setInt(paramIndex++, Integer.parseInt(query.trim()));
                }
                pstmt.setString(paramIndex++, "%" + query.trim() + "%");
            }
            pstmt.setInt(paramIndex++, limit);
            pstmt.setInt(paramIndex, offset);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    statuses.add(mapResultSetToOrderStatus(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error loading admin order statuses", e);
        }
        return statuses;
    }

    @Override
    public int countForAdmin(String query) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM order_statuses");
        boolean hasQuery = query != null && !query.isBlank();
        boolean numericQuery = hasQuery && query.trim().matches("\\d+");
        if (hasQuery) {
            sql.append(" WHERE ");
            if (numericQuery) {
                sql.append("id = ? OR name ILIKE ?");
            } else {
                sql.append("name ILIKE ?");
            }
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (hasQuery) {
                if (numericQuery) {
                    pstmt.setInt(paramIndex++, Integer.parseInt(query.trim()));
                }
                pstmt.setString(paramIndex, "%" + query.trim() + "%");
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting admin order statuses", e);
        }
        return 0;
    }

    private OrderStatus mapResultSetToOrderStatus(ResultSet rs) throws SQLException {
        return new OrderStatus(rs.getInt("id"), rs.getString("name"));
    }
}
