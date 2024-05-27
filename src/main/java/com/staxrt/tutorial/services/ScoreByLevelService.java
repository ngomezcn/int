package com.staxrt.tutorial.services;

import com.staxrt.tutorial.dto.QuestionOptionsDTO;
import com.staxrt.tutorial.dto.ScoreByLevelDTO;
import com.staxrt.tutorial.entity.QuestionOptionsEntity;
import com.staxrt.tutorial.entity.ScoreByLevelEntity;
import com.staxrt.tutorial.repository.ScoreByLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreByLevelService {

    @Autowired
    private ScoreByLevelRepository scoreByLevelRepository;

    public ScoreByLevelEntity createScoreByLevel(ScoreByLevelEntity scoreByLevel) {
        return scoreByLevelRepository.save(scoreByLevel);
    }

    public List<ScoreByLevelEntity> getAllScoreByLevel() {
        return scoreByLevelRepository.findAll();
    }

    public ScoreByLevelDTO convertToDTO(ScoreByLevelEntity scoreByLevel)
    {
        ScoreByLevelDTO dto = new ScoreByLevelDTO();

        dto.setId(scoreByLevel.getId());
        dto.setName(scoreByLevel.getName());
        dto.setEducationLevelScores(scoreByLevel.getEducationLevelScores());

        return dto;
    }
}
