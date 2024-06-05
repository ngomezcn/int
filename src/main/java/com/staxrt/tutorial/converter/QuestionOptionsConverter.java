package com.staxrt.tutorial.converter;

import com.staxrt.tutorial.dto.entities.QuestionOptionsDTO;
import com.staxrt.tutorial.entity.QuestionOptionsEntity;
import org.springframework.stereotype.Component;

@Component
public class QuestionOptionsConverter {

    public QuestionOptionsDTO convertToDTO(QuestionOptionsEntity QuestionOptions) {
        QuestionOptionsDTO dto = new QuestionOptionsDTO();

        dto.setId(QuestionOptions.getId());
        dto.setTimeLimit(QuestionOptions.getTimeLimit());
        dto.setAnswerType(QuestionOptions.getAnswerType());

        return dto;
    }
}
