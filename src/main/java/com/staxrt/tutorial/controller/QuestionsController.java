package com.staxrt.tutorial.controller;

import com.staxrt.tutorial.dto.QuestionDTO;
import com.staxrt.tutorial.exception.ResourceNotFoundException;
import com.staxrt.tutorial.services.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionsController {

    @Autowired
    private QuestionsService questionService;

    @GetMapping("/all")
    public ResponseEntity<List<QuestionDTO>> getAllCategories() {
        return ResponseEntity.ok(
                questionService.getAllQuestions()
        );
    }

    @GetMapping("/question-details/{id}")
    public ResponseEntity<QuestionDTO> getQuestionDetails(@PathVariable String id) throws ResourceNotFoundException {

        return ResponseEntity.ok(
                questionService.getQuestionById(Long.valueOf(id))
        );
    }
}
