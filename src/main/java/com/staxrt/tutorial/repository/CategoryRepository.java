package com.staxrt.tutorial.repository;

import com.staxrt.tutorial.entity.question.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    // Métodos de consulta personalizados si es necesario
}
