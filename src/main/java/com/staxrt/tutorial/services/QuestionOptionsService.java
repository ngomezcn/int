package com.staxrt.tutorial.services;

import com.staxrt.tutorial.dto.questionDTO.QuestionOptionsDTO;
import com.staxrt.tutorial.entity.question.QuestionOptionsEntity;
import org.springframework.stereotype.Service;

@Service
public class QuestionOptionsService {

    public QuestionOptionsDTO convertToDTO(QuestionOptionsEntity QuestionOptions)
    {
        QuestionOptionsDTO dto = new QuestionOptionsDTO();

        dto.setId(QuestionOptions.getId());
        dto.setTimeLimit(QuestionOptions.getTimeLimit());
        dto.setAnswerType(QuestionOptions.getAnswerType());

        return dto;
    }
}
