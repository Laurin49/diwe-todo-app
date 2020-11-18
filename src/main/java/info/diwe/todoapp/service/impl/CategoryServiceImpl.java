package info.diwe.todoapp.service.impl;

import info.diwe.todoapp.exception.ResourceNotFoundException;
import info.diwe.todoapp.model.Category;
import info.diwe.todoapp.repository.CategoryRepository;
import info.diwe.todoapp.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> readCategories() {

        return categoryRepository.findAll();
    }

    @Override
    public Category readCategory(Long id) {
        Optional<Category> optCategory = categoryRepository.findById(id);
        if (!optCategory.isPresent()) {
            throw new ResourceNotFoundException("Category mit id: " + id + " nicht gefunden ...");
        }
        return optCategory.get();
    }

    @Override
    public Category createCategory(Category category) {
        Category result = categoryRepository.save(category);
        return result;
    }

    @Override
    public Category updateCategory(Category category) {
        Optional<Category> optCategory = categoryRepository.findById(category.getId());
        if (!optCategory.isPresent()) {
            throw new ResourceNotFoundException("Category mit id: " + category.getId() + " nicht gefunden ...");
        }
        Category result = categoryRepository.save(category);
        return result;
    }

    @Override
    public void deleteCategory(Long id) {
        Optional<Category> optCategory = categoryRepository.findById(id);
        if (!optCategory.isPresent()) {
            throw new ResourceNotFoundException("Category mit id: " + id + " nicht gefunden ...");
        }
        categoryRepository.deleteById(id);
    }
}
