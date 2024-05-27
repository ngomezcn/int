package com.staxrt.tutorial.controller;

import com.staxrt.tutorial.entity.EducationLevelEntity;
import com.staxrt.tutorial.repository.EducationLevelRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EducationLevelController {

    @Autowired
    private EducationLevelRepository educationLevelRepository;

    @GetMapping("/education-levels")
    public List<EducationLevelEntity> getAllEducationLevels() {
        return educationLevelRepository.findAll();
    }
}
