package com.staxrt.tutorial.dto.entities;

import com.staxrt.tutorial.enums.EducationLevel;

import javax.persistence.EnumType;
import javax.persistence.MapKeyEnumerated;
import java.util.Map;

public class ScoreByLevelDTO {
    private Long id;

    private String name;

    @MapKeyEnumerated(EnumType.STRING)
    private Map<EducationLevel, Integer> educationLevelScores;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<EducationLevel, Integer> getEducationLevelScores() {
        return educationLevelScores;
    }

    public void setEducationLevelScores(Map<EducationLevel, Integer> educationLevelScores) {
        this.educationLevelScores = educationLevelScores;
    }
}
