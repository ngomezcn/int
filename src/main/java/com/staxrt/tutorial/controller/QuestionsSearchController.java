package com.staxrt.tutorial.controller;

import com.staxrt.tutorial.dto.CategoryDropdownDTO;
import com.staxrt.tutorial.dto.QuestionSearchResultDTO;
import com.staxrt.tutorial.dto.QuestionSearchFilterDTO;
import com.staxrt.tutorial.services.QuestionsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionsSearchController {

    @Autowired
    QuestionsSearchService questionsSearchService;

    @PostMapping("/search-result")
    public ResponseEntity<List<QuestionSearchResultDTO>> questionsSearch(@RequestBody Optional<QuestionSearchFilterDTO> /* Body request may be null */ filter) {

        return ResponseEntity.ok(questionsSearchService.getSearchResult(filter));
    }

    @GetMapping("/categories-dropdown")
    public ResponseEntity<List<CategoryDropdownDTO>> searchCategories() {

        return ResponseEntity.ok(questionsSearchService.getCategoriesSearch());
    }
}
