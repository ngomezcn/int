package com.staxrt.tutorial.repository;

import com.staxrt.tutorial.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CategoryRelationRepository extends JpaRepository<CategoryEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM category_relations WHERE child_id = :childId", nativeQuery = true)
    void deleteByChild(@Param("childId") long childId);
}
