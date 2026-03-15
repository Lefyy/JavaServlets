package app.service.impl;

import app.model.Product;
import app.repository.ProductRepository;
import app.service.ProductService;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findByCategoryId(Integer categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Product> findByNameContaining(String name) {
        return productRepository.findByNameContaining(name);
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void update(Product product) {
        productRepository.update(product);
    }

    @Override
    public boolean delete(Integer id) {
        return productRepository.delete(id);
    }
}
