package app.service.impl;

import app.model.Category;
import app.repository.CategoryRepository;
import app.service.CategoryService;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void update(Category category) {
        categoryRepository.update(category);
    }

    @Override
    public boolean delete(Integer id) {
        return categoryRepository.delete(id);
    }
}
