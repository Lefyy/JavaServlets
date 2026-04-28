package app.repository;

import app.model.Product;

import java.util.List;

public interface ProductRepository extends Repository<Product> {

    List<Product> findByCategoryId(Integer categoryId);

    List<Product> findCatalog(String category, String sort, int limit, int offset);

    int countCatalog(String category);

    List<Product> findByNameContaining(String name);

    void updateQuantity(Integer productId, int newQuantity);
}
