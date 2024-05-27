package com.staxrt.tutorial.repository;

import com.staxrt.tutorial.entity.QuestionTimeLimitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionTimeLimitRepository extends JpaRepository<QuestionTimeLimitEntity, Long> {


}
