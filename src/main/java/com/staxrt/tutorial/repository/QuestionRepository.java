package com.staxrt.tutorial.repository;

import com.staxrt.tutorial.entity.question.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

}
