package com.staxrt.tutorial.controller;

import com.staxrt.tutorial.entity.QuestionTimeLimitEntity;
import com.staxrt.tutorial.repository.QuestionTimeLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class QuestionTimeLimitController {

    @Autowired
    private QuestionTimeLimitRepository timeLimitRepository;

    @GetMapping("/time-limits")
    public List<QuestionTimeLimitEntity> getAllTimeLimits() {

        List<QuestionTimeLimitEntity> result = timeLimitRepository.findAll();
        Collections.sort(result);

        return result;
    }
}
