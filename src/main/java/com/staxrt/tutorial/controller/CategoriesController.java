package com.staxrt.tutorial.controller;

import com.staxrt.tutorial.dto.AddCategoryDTO;
import com.staxrt.tutorial.dto.AddCategoryRelationDTO;
import com.staxrt.tutorial.entity.CategoryEntity;
import com.staxrt.tutorial.repository.CategoryRepository;
import com.staxrt.tutorial.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoriesController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/remove")
    public ResponseEntity<?> deleteCategoryByName(@RequestBody AddCategoryDTO dto) {

        if (categoryService.deleteCategoryByName(dto.getName())) ; // TODO: Improve this
        {
            return ResponseEntity.ok()
                    .build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<CategoryEntity> createCategory(@RequestBody AddCategoryDTO dto) {
        CategoryEntity savedCategory = categoryService.addCategory(dto.getName());
        return ResponseEntity.ok(
                savedCategory
        );
    }

    @GetMapping("/{name}")
    public ResponseEntity<CategoryEntity> getCategoryByName(@PathVariable String name) {
        return categoryService.getCategoryByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryEntity>> getAllCategories() {
        return ResponseEntity.ok(
                categoryService.getAllCategories()
        );
    }

    @PostMapping("/addRelation")
    public ResponseEntity<Void> addCategoryRelation(@RequestBody AddCategoryRelationDTO dto) {
        categoryService.addCategoryRelation(dto.getParentName(), dto.getChildName());
        return ResponseEntity.ok()
                .build();
    }
}
