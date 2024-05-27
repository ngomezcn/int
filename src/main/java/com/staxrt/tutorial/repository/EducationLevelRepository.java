package com.staxrt.tutorial.repository;

import com.staxrt.tutorial.entity.EducationLevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationLevelRepository extends JpaRepository<EducationLevelEntity, Long> {



}
