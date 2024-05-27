package com.staxrt.tutorial.controller;

import com.staxrt.tutorial.entity.question.CategoryEntity;
import com.staxrt.tutorial.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<CategoryEntity> createCategory(@RequestBody CategoryEntity category) {
        CategoryEntity savedCategory = categoryService.saveCategory(category);
        return ResponseEntity.ok(savedCategory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryEntity> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryEntity>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

        @PostMapping("/{parentId}/addChild/{childId}")
    public ResponseEntity<Void> addCategoryRelation(@PathVariable Long parentId, @PathVariable Long childId) {
        categoryService.addCategoryRelation(parentId, childId);
        return ResponseEntity.ok().build();
    }

    // Métodos adicionales para búsquedas complejas en el grafo
}
