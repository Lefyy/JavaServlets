package app.repository.impl;

import app.config.DatabaseConnection;
import app.model.Product;
import app.repository.ProductRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public Product save(Product product) {
        String sql = "INSERT INTO products (name, price, quantity, category_id, image_url) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            mapProductToStatement(pstmt, product);
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении продукта", e);
        }
        return product;
    }

    @Override
    public Optional<Product> findById(Integer id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToProduct(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске продукта по ID", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении списка всех продуктов", e);
        }
        return products;
    }

    @Override
    public void update(Product product) {
        String sql = "UPDATE products SET name = ?, price = ?, quantity = ?, category_id = ?, image_url = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            mapProductToStatement(pstmt, product);
            pstmt.setInt(6, product.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении продукта", e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении продукта", e);
        }
    }

    @Override
    public List<Product> findByCategoryId(Integer categoryId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, categoryId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapResultSetToProduct(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске по категории", e);
        }
        return products;
    }

    @Override
    public List<Product> findCatalog(String category, String sort, int limit, int offset) {
        List<Product> products = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT p.* FROM products p LEFT JOIN order_items oi ON oi.product_id = p.id"
        );
        boolean hasCategory = category != null && !category.isBlank();
        if (hasCategory) {
            sql.append(" WHERE p.category_id = ?");
        }
        sql.append(" GROUP BY p.id");
        sql.append(resolveOrderBy(sort));
        sql.append(" LIMIT ? OFFSET ?");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (hasCategory) {
                pstmt.setInt(paramIndex++, Integer.parseInt(category));
            }
            pstmt.setInt(paramIndex++, limit);
            pstmt.setInt(paramIndex, offset);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapResultSetToProduct(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выборке каталога", e);
        }
        return products;
    }

    @Override
    public int countCatalog(String category) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM products");
        boolean hasCategory = category != null && !category.isBlank();
        if (hasCategory) {
            sql.append(" WHERE category_id = ?");
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            if (hasCategory) {
                pstmt.setInt(1, Integer.parseInt(category));
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при подсчете каталога", e);
        }
        return 0;
    }

    @Override
    public List<Product> findByNameContaining(String name) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name ILIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + name + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapResultSetToProduct(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске по названию", e);
        }
        return products;
    }

    @Override
    public void updateQuantity(Integer productId, int newQuantity) {
        String sql = "UPDATE products SET quantity = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, newQuantity);
            pstmt.setInt(2, productId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении количества", e);
        }
    }

    @Override
    public List<Product> findForAdmin(String query, int limit, int offset) {
        List<Product> products = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM products");
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
                    products.add(mapResultSetToProduct(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error loading admin products", e);
        }
        return products;
    }

    @Override
    public int countForAdmin(String query) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM products");
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
            throw new RuntimeException("Error counting admin products", e);
        }
        return 0;
    }

    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        return new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getBigDecimal("price"),
                rs.getInt("quantity"),
                rs.getInt("category_id"),
                rs.getString("image_url")
        );
    }

    private void mapProductToStatement(PreparedStatement pstmt, Product product) throws SQLException {
        pstmt.setString(1, product.getName());
        pstmt.setBigDecimal(2, product.getPrice());
        pstmt.setInt(3, product.getQuantity());
        pstmt.setInt(4, product.getCategoryId());
        pstmt.setString(5, product.getImageUrl());
    }

    private String resolveOrderBy(String sort) {
        if ("price_asc".equals(sort)) {
            return " ORDER BY p.price ASC";
        }
        if ("price_desc".equals(sort)) {
            return " ORDER BY p.price DESC";
        }
        if ("popularity".equals(sort)) {
            return " ORDER BY COALESCE(SUM(oi.quantity), 0) DESC";
        }
        return " ORDER BY p.id ASC";
    }

}
