package com.phuongnh.personal.library_management_system.controller;

import com.phuongnh.personal.library_management_system.dto.BookDTO;
import com.phuongnh.personal.library_management_system.model.Book;

import com.phuongnh.personal.library_management_system.model.Category;
import com.phuongnh.personal.library_management_system.dto.CategoryDTO;
import com.phuongnh.personal.library_management_system.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/categories")
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

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable UUID id) {
        Category category = categoryService.getCategoryById(id);
        CategoryDTO categoryDTO = categoryService.convertToDTO(category);
        return ResponseEntity.ok(categoryDTO);
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<List<BookDTO>> getBooksByCategory(@PathVariable UUID id) {
        List<BookDTO> books = categoryService.getBooksByCategoryId(id);
        return ResponseEntity.ok(books);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','SUPERUSER')")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO.getId(), categoryDTO.getName(), new ArrayList<>());
        Category createdCategory = categoryService.createCategory(category);
        CategoryDTO createdCategoryDTO = categoryService.convertToDTO(createdCategory);
        return new ResponseEntity<>(createdCategoryDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','SUPERUSER')")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable UUID id, @RequestBody CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO.getId(), categoryDTO.getName(), new ArrayList<>());
        Category updatedCategory = categoryService.updateCategory(id, category);
        CategoryDTO updatedCategoryDTO = categoryService.convertToDTO(updatedCategory);
        return ResponseEntity.ok(updatedCategoryDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','SUPERUSER')")
    public ResponseEntity<?> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}

