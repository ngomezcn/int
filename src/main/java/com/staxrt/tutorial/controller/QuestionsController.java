package com.staxrt.tutorial.controller;

import com.staxrt.tutorial.dto.QuestionDTO;
import com.staxrt.tutorial.dto.QuestionSearchDTO;
import com.staxrt.tutorial.dto.QuestionSearchFilterDTO;
import com.staxrt.tutorial.services.QuestionsSearchService;
import com.staxrt.tutorial.services.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionsController {

    @Autowired
    private QuestionsService questionService;

    @GetMapping("/all")
    public ResponseEntity<List<QuestionDTO>> getAllCategories() {
        return ResponseEntity.ok(questionService.getAllQuestions());
    }
}
