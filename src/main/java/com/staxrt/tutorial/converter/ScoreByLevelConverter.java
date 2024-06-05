package com.staxrt.tutorial.converter;

import com.staxrt.tutorial.dto.entities.ScoreByLevelDTO;
import com.staxrt.tutorial.entity.ScoreByLevelEntity;
import org.springframework.stereotype.Component;

@Component
public class ScoreByLevelConverter {

    public ScoreByLevelDTO convertToDTO(ScoreByLevelEntity scoreByLevel) {
        ScoreByLevelDTO dto = new ScoreByLevelDTO();

        dto.setId(scoreByLevel.getId());
        dto.setName(scoreByLevel.getName());
        dto.setEducationLevelScores(scoreByLevel.getEducationLevelScores());

        return dto;
    }
}
