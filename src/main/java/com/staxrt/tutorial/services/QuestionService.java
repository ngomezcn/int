package com.staxrt.tutorial.services;

import com.staxrt.tutorial.dto.AnswerDTO;
import com.staxrt.tutorial.dto.questionDTO.CategoryDTO;
import com.staxrt.tutorial.dto.questionDTO.QuestionDTO;
import com.staxrt.tutorial.dto.questionDTO.QuestionSettingsDTO;
import com.staxrt.tutorial.entity.question.CategoryEntity;
import com.staxrt.tutorial.entity.question.QuestionEntity;
import com.staxrt.tutorial.repository.CategoryRepository;
import com.staxrt.tutorial.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionSettingsService questionSettingsService;

    List<QuestionEntity> questions;


    private QuestionDTO convertToDTO(QuestionEntity question) {
        QuestionDTO dto = new QuestionDTO();
        List<CategoryDTO> categoriesDto = new ArrayList<>();
        List<AnswerDTO> answersDto = new ArrayList<>();
        QuestionSettingsDTO questionsSettingsDto = new QuestionSettingsDTO();

        categoriesDto = question.getCategories().stream()
                .map(categoryService::convertToDTO)
                .collect(Collectors.toList());

        answersDto = question.getAnswers().stream()
                .map(answerService::convertToDTO)
                .collect(Collectors.toList());

        questionsSettingsDto = questionSettingsService.convertToDTO(question.getQuestionSettings());

        dto.setId(question.getId());
        dto.setQuestion(question.getQuestion());

        dto.setCategories(categoriesDto);
        dto.setAnswers(answersDto);
        dto.setQuestionSettings(questionsSettingsDto);

        return dto;
    }

    @Autowired
    QuestionRepository questionRepository;

    public List<QuestionDTO> getAllQuestions() {
        questions = questionRepository.findAll();

        List<QuestionDTO> dtos = questions.stream()
                .map(question -> convertToDTO(question))
                .collect(Collectors.toList());

        return dtos;
    }
}
