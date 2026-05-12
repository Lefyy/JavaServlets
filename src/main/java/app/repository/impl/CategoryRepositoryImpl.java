package app.repository.impl;

import app.config.DatabaseConnection;
import app.model.Category;
import app.repository.CategoryRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryRepositoryImpl implements CategoryRepository {

    @Override
    public Category save(Category category) {
        String sql = "INSERT INTO categories (name) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, category.getName());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    category.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении категории", e);
        }
        return category;
    }

    @Override
    public Optional<Category> findById(Integer id) {
        String sql = "SELECT * FROM categories WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToCategory(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске категории по ID", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                categories.add(mapResultSetToCategory(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении списка категорий", e);
        }
        return categories;
    }

    @Override
    public void update(Category category) {
        String sql = "UPDATE categories SET name = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, category.getName());
            pstmt.setInt(2, category.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении категории", e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM categories WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении категории", e);
        }
    }

    @Override
    public List<Category> findForAdmin(String query, int limit, int offset) {
        List<Category> categories = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM categories");
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
                    categories.add(mapResultSetToCategory(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error loading admin categories", e);
        }
        return categories;
    }

    @Override
    public int countForAdmin(String query) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM categories");
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
            throw new RuntimeException("Error counting admin categories", e);
        }
        return 0;
    }

    private Category mapResultSetToCategory(ResultSet rs) throws SQLException {
        return new Category(rs.getInt("id"), rs.getString("name"));
    }
}
