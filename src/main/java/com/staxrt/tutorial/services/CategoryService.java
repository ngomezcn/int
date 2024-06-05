package com.staxrt.tutorial.services;

import com.staxrt.tutorial.converter.CategoryConverter;
import com.staxrt.tutorial.dto.AddCategoryDTO;
import com.staxrt.tutorial.entity.CategoryEntity;
import com.staxrt.tutorial.repository.CategoryRelationRepository;
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

    @Autowired
    private CategoryConverter categoryConverter;

    @Autowired
    private CategoryRelationRepository categoryRelationRepository;

    private Graph<CategoryEntity, DefaultEdge> categoryGraph;

    public CategoryService() {
        categoryGraph = new SimpleDirectedGraph<>(DefaultEdge.class);
    }

    @Transactional
    public boolean deleteCategoryByName(String categoryName) {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findByName(categoryName);

        if (optionalCategory.isPresent()) {
            CategoryEntity category = optionalCategory.get();

            if (category.getChildren().isEmpty()) {

                categoryRelationRepository.deleteByChild(category.getId());

                categoryRepository.delete(category);

            } else {
                throw new RuntimeException("No se puede eliminar la categoría porque tiene hijos.");
            }
        } else {
            throw new RuntimeException("No se encontró la categoría con el nombre especificado.");
        }

        return true;
    }

    @Transactional
    public CategoryEntity addCategory(String name) {
        CategoryEntity category = categoryConverter.convertToEntity(name);
        categoryRepository.save(category);
        return category;
    }

    public Optional<CategoryEntity> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Optional<CategoryEntity> getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    public void addCategoryRelation(String parentName, String childName) {
        Optional<CategoryEntity> parent = categoryRepository.findByName(parentName);
        Optional<CategoryEntity> child = categoryRepository.findByName(childName);

        if (!parent.isPresent()) {
            parent = Optional.ofNullable(this.addCategory(parentName));
        }

        if (!child.isPresent()) {
            child = Optional.ofNullable(this.addCategory(childName));
        }

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
