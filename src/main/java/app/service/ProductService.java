package app.service;

import app.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();

    Optional<Product> findById(Integer id);

    List<Product> findByCategoryId(Integer categoryId);

    List<Product> findCatalog(String category, String sort, int limit, int offset);

    int countCatalog(String category);

    List<Product> findByNameContaining(String name);

    Product create(Product product);

    void update(Product product);

    boolean delete(Integer id);
}
