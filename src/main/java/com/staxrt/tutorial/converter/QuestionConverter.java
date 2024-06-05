package com.staxrt.tutorial.converter;

import com.staxrt.tutorial.dto.QuestionDTO;
import com.staxrt.tutorial.dto.QuestionSearchResultDTO;
import com.staxrt.tutorial.dto.entities.AnswerDTO;
import com.staxrt.tutorial.dto.entities.CategoryDTO;
import com.staxrt.tutorial.dto.entities.QuestionOptionsDTO;
import com.staxrt.tutorial.dto.entities.ScoreByLevelDTO;
import com.staxrt.tutorial.entity.CategoryEntity;
import com.staxrt.tutorial.entity.QuestionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionConverter {

    @Autowired
    private CategoryConverter categoryConverter;

    @Autowired
    private AnswerConverter answerConverter;

    @Autowired
    private QuestionOptionsConverter questionOptionsConverter;

    @Autowired
    private ScoreByLevelConverter scoreByLevelConverter;

    public QuestionDTO convertToDTO(QuestionEntity question) {
        QuestionDTO dto = new QuestionDTO();
        List<CategoryDTO> categoriesDto = new ArrayList<>();
        List<AnswerDTO> answersDto = new ArrayList<>();
        QuestionOptionsDTO questionsOptionsDto = new QuestionOptionsDTO();
        ScoreByLevelDTO scoreByLevelDTO = new ScoreByLevelDTO();

        categoriesDto = question.getCategories().stream()
                .map(categoryConverter::convertToDTO)
                .collect(Collectors.toList());

        answersDto = question.getAnswerEntities().stream()
                .map(answerConverter::convertToDTO)
                .collect(Collectors.toList());

        questionsOptionsDto = questionOptionsConverter.convertToDTO(question.getQuestionOptionsEntity());
        scoreByLevelDTO = scoreByLevelConverter.convertToDTO(question.getScoreByLevel());

        dto.setId(question.getId());
        dto.setQuestion(question.getQuestion());
        dto.setMediaPath(question.getMediaPath());

        dto.setCategories(categoriesDto);
        dto.setAnswers(answersDto);
        dto.setQuestionOptions(questionsOptionsDto);
        dto.setScoreByLevel(scoreByLevelDTO);

        return dto;
    }

    public QuestionSearchResultDTO convertToQuestionSearchDTO(QuestionEntity question) {
        QuestionSearchResultDTO dto = new QuestionSearchResultDTO();

        dto.setId(question.getId());
        dto.setQuestion(question.getQuestion());
        dto.setMediaPath(question.getMediaPath());

        dto.setAnswerType(question.getQuestionOptionsEntity().getAnswerType());
        dto.setTimeLimit(question.getQuestionOptionsEntity().getTimeLimit());

        dto.setCreatedBy(question.getCreatedBy());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dto.setCreatedAt(dateFormat.format(question.getCreatedAt()));

        List<String> categories = new ArrayList<>();
        for (CategoryEntity category : question.getCategories()) {
            categories.add(category.getName());
        }
        dto.setCategories(categories);

        return dto;
    }
}
