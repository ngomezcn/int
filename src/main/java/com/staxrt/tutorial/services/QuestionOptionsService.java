package com.staxrt.tutorial.services;

import com.staxrt.tutorial.dto.QuestionOptionsDTO;
import com.staxrt.tutorial.entity.QuestionOptionsEntity;
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
