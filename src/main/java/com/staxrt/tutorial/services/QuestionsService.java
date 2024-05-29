package com.staxrt.tutorial.services;

import com.staxrt.tutorial.converter.QuestionConverter;
import com.staxrt.tutorial.dto.*;
import com.staxrt.tutorial.entity.QuestionEntity;
import com.staxrt.tutorial.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionsService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionConverter questionConverter;

    public List<QuestionDTO> getAllQuestions() {
        List<QuestionEntity> questions = questionRepository.findAll();

        return questions.stream()
                .map(questionConverter::convertToDTO)
                .collect(Collectors.toList());
    }
}
