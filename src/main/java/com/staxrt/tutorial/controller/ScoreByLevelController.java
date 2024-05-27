package com.staxrt.tutorial.controller;

import com.staxrt.tutorial.entity.ScoreByLevelEntity;
import com.staxrt.tutorial.services.ScoreByLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/score-by-level")
public class ScoreByLevelController {

    @Autowired
    private ScoreByLevelService scoreByLevelService;

    @PostMapping
    public ResponseEntity<ScoreByLevelEntity> createScoreByLevel(@RequestBody ScoreByLevelEntity scoreByLevel) {
        ScoreByLevelEntity newScoreByLevel = scoreByLevelService.createScoreByLevel(scoreByLevel);
        return new ResponseEntity<>(newScoreByLevel, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScoreByLevelEntity>> getAllScoreByLevel() {
        List<ScoreByLevelEntity> scoreByLevelList = scoreByLevelService.getAllScoreByLevel();
        return new ResponseEntity<>(scoreByLevelList, HttpStatus.OK);
    }
}
