package com.staxrt.tutorial.repository;

import com.staxrt.tutorial.entity.ScoreByLevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreByLevelRepository extends JpaRepository<ScoreByLevelEntity, Long> {
}
