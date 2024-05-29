package com.staxrt.tutorial.services;

import com.staxrt.tutorial.entity.CategoryEntity;
import com.staxrt.tutorial.repository.CategoryRepository;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    private Graph<CategoryEntity, DefaultEdge> categoryGraph;

    public CategoryService() {
        categoryGraph = new SimpleDirectedGraph<>(DefaultEdge.class);
    }

    public CategoryEntity saveCategory(CategoryEntity category) {
        return categoryRepository.save(category);
    }

    public Optional<CategoryEntity> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    public void addCategoryRelation(Long parentId, Long childId) {
        Optional<CategoryEntity> parent = categoryRepository.findById(parentId);
        Optional<CategoryEntity> child = categoryRepository.findById(childId);

        if (parent.isPresent() && child.isPresent()) {
            CategoryEntity parentCategory = parent.get();
            CategoryEntity childCategory = child.get();

            parentCategory.getChildren().add(childCategory);
            childCategory.getParents().add(parentCategory);

            categoryRepository.save(parentCategory);
            categoryRepository.save(childCategory);

            // Update the graph
            categoryGraph.addVertex(parentCategory);
            categoryGraph.addVertex(childCategory);
            categoryGraph.addEdge(parentCategory, childCategory);
        }
    }

    public Graph<CategoryEntity, DefaultEdge> getCategoryGraph() {
        // Rebuild the graph from the database if necessary
        if (categoryGraph.vertexSet().isEmpty()) {
            List<CategoryEntity> allCategories = categoryRepository.findAll();
            allCategories.forEach(categoryGraph::addVertex);
            for (CategoryEntity category : allCategories) {
                for (CategoryEntity child : category.getChildren()) {
                    categoryGraph.addEdge(category, child);
                }
            }
        }
        return categoryGraph;
    }


}
