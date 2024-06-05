package com.staxrt.tutorial.services;

import com.staxrt.tutorial.converter.QuestionConverter;
import com.staxrt.tutorial.dto.*;
import com.staxrt.tutorial.entity.QuestionEntity;
import com.staxrt.tutorial.exception.ResourceNotFoundException;
import com.staxrt.tutorial.repository.QuestionRepository;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;
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

    public QuestionDTO getQuestionById(Long id) throws ResourceNotFoundException {

        Optional<QuestionEntity> question = questionRepository.findById(id);

        if (question.isPresent()) {
            return questionConverter.convertToDTO(question.get());
        }
        throw new ResourceNotFoundException("No question found");
    }
}
