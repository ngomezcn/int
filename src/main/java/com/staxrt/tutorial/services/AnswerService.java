package com.staxrt.tutorial.services;

import com.staxrt.tutorial.dto.AnswerDTO;
import com.staxrt.tutorial.entity.AnswerEntity;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

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
