package com.staxrt.tutorial.controller;

import com.staxrt.tutorial.dto.QuestionSearchCategoryDTO;
import com.staxrt.tutorial.dto.QuestionSearchResultDTO;
import com.staxrt.tutorial.dto.QuestionSearchFilterDTO;
import com.staxrt.tutorial.services.QuestionsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionsSearchController {

    @Autowired
    QuestionsSearchService questionsSearchService;

    @PostMapping("/search-result")
    public ResponseEntity<List<QuestionSearchResultDTO>> questionsSearch(@RequestBody Optional<QuestionSearchFilterDTO> filter) {

        return ResponseEntity.ok(questionsSearchService.getSearchResult(filter));
    }

    @GetMapping("/search-categories")
    public ResponseEntity<List<QuestionSearchCategoryDTO>> searchCategories() {

        return ResponseEntity.ok(questionsSearchService.getCategoriesSearch());
    }
}
