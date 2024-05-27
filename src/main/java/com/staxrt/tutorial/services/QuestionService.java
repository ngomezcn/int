package com.staxrt.tutorial.services;

import com.staxrt.tutorial.dto.*;
import com.staxrt.tutorial.entity.QuestionEntity;
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
    private QuestionOptionsService questionOptionsService;

    @Autowired
    private ScoreByLevelService scoreByLevelService;

    @Autowired
    private QuestionRepository questionRepository;

    private QuestionDTO convertToDTO(QuestionEntity question) {
        QuestionDTO dto = new QuestionDTO();
        List<CategoryDTO> categoriesDto = new ArrayList<>();
        List<AnswerDTO> answersDto = new ArrayList<>();
        QuestionOptionsDTO questionsOptionsDto = new QuestionOptionsDTO();
        ScoreByLevelDTO scoreByLevelDTO = new ScoreByLevelDTO();

        categoriesDto = question.getCategories().stream()
                .map(categoryService::convertToDTO)
                .collect(Collectors.toList());

        answersDto = question.getAnswers().stream()
                .map(answerService::convertToDTO)
                .collect(Collectors.toList());

        questionsOptionsDto = questionOptionsService.convertToDTO(question.getQuestionOptions());
        scoreByLevelDTO = scoreByLevelService.convertToDTO(question.getScoreByLevel());

        dto.setId(question.getId());
        dto.setQuestion(question.getQuestion());
        dto.setMediaPath(question.getMediaPath());

        dto.setCategories(categoriesDto);
        dto.setAnswers(answersDto);
        dto.setQuestionOptions(questionsOptionsDto);
        dto.setScoreByLevel(scoreByLevelDTO);

        return dto;
    }

    public List<QuestionDTO> getAllQuestions() {
        List<QuestionEntity> questions = questionRepository.findAll();

        return questions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
