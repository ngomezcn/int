package com.staxrt.tutorial.services;

import com.staxrt.tutorial.dto.questionDTO.CategoryDTO;
import com.staxrt.tutorial.dto.questionDTO.QuestionSettingsDTO;
import com.staxrt.tutorial.entity.question.CategoryEntity;
import com.staxrt.tutorial.entity.question.QuestionSettingsEntity;
import org.springframework.stereotype.Service;

@Service
public class QuestionSettingsService {

    public QuestionSettingsDTO convertToDTO(QuestionSettingsEntity questionSettings)
    {
        QuestionSettingsDTO dto = new QuestionSettingsDTO();

        dto.setId(questionSettings.getId());
        dto.setTimeLimit(questionSettings.getTimeLimit());
        dto.setAnswerType(questionSettings.getAnswerType());

        return dto;
    }
}
