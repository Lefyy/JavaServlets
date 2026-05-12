package app.repository;

import app.model.Category;
import java.util.List;

public interface CategoryRepository extends Repository<Category> {
    List<Category> findForAdmin(String query, int limit, int offset);

    int countForAdmin(String query);
}
