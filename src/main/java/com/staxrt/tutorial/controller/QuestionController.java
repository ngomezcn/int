package com.staxrt.tutorial.controller;

import com.staxrt.tutorial.dto.questionDTO.QuestionDTO;
import com.staxrt.tutorial.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/all")
    public ResponseEntity<List<QuestionDTO>> getAllCategories() {
        return ResponseEntity.ok(questionService.getAllQuestions());
    }
}
