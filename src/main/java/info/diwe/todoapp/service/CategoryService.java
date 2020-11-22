package info.diwe.todoapp.service;

import info.diwe.todoapp.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> readCategories();
    Category readCategory(Long id);
    Category createCategory(Category category);
    Category updateCategory(Category category);
    void deleteCategory(Long id);
    Category findByName(String name);
    Category findFirstBy();

}
