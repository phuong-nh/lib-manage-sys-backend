package com.phuongnh.personal.library_management_system.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(UUID id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(UUID id, Category category) {
        Category existingCategory = getCategoryById(id);
        existingCategory.setName(category.getName());
        existingCategory.setBooks(category.getBooks());
        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(UUID id) {
        categoryRepository.delete(getCategoryById(id));
    }
}
