package com.staxrt.tutorial.converter;

import com.staxrt.tutorial.dto.entities.AnswerDTO;
import com.staxrt.tutorial.entity.AnswerEntity;
import org.springframework.stereotype.Component;

@Component
public class AnswerConverter {
    public AnswerDTO convertToDTO(AnswerEntity answer)
    {
        AnswerDTO dto = new AnswerDTO();

        dto.setId(answer.getId());
        dto.setAnswer(answer.getAnswer());
        dto.setNumber(answer.getNumber());
        dto.setCorrect(answer.getCorrect());

        return dto;
    }
}
