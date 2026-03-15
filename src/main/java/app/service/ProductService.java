package app.service;

import app.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();

    Optional<Product> findById(Integer id);

    List<Product> findByCategoryId(Integer categoryId);

    List<Product> findByNameContaining(String name);

    /** Только для staff: создание продукта */
    Product create(Product product);

    /** Только для staff: обновление продукта */
    void update(Product product);

    /** Только для staff: удаление продукта */
    boolean delete(Integer id);
}
