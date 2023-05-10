package com.phuongnh.personal.library_management_system.Category;

import com.phuongnh.personal.library_management_system.Book.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryDTO> categoryDTOs = categories.stream()
                .map(categoryService::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoryDTOs);
    }

    @GetMapping("/{name}")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name) {
        Category category = categoryService.getCategoryByName(name);
        CategoryDTO categoryDTO = categoryService.convertToDTO(category);
        return ResponseEntity.ok(categoryDTO);
    }

    @GetMapping("/{name}/books")
    public ResponseEntity<List<Book>> getBooksByCategory(@PathVariable String name) {
        List<Book> books = categoryService.getBooksByCategoryName(name);
        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO.getId(), categoryDTO.getName(), new ArrayList<>());
        Category createdCategory = categoryService.createCategory(category);
        CategoryDTO createdCategoryDTO = categoryService.convertToDTO(createdCategory);
        return new ResponseEntity<>(createdCategoryDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{name}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable String name, @RequestBody CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO.getId(), categoryDTO.getName(), new ArrayList<>());
        Category updatedCategory = categoryService.updateCategoryByName(name, category);
        CategoryDTO updatedCategoryDTO = categoryService.convertToDTO(updatedCategory);
        return ResponseEntity.ok(updatedCategoryDTO);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String name) {
        categoryService.deleteCategoryByName(name);
        return ResponseEntity.noContent().build();
    }
}

