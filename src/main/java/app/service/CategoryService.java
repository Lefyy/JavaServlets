package app.service;

import app.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Optional<Category> findById(Integer id);

    List<Category> findAll();

    List<Category> findForAdmin(String query, int limit, int offset);

    int countForAdmin(String query);

    Category save(Category category);

    void update(Category category);

    boolean delete(Integer id);
}
