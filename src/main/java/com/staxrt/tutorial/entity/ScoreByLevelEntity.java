package com.staxrt.tutorial.entity;

import com.staxrt.tutorial.enums.EducationLevel;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "score_by_level")
public class ScoreByLevelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ElementCollection
    @CollectionTable(name = "ScoreByLevel_EducationLevel")
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