package com.staxrt.tutorial.services;

import com.staxrt.tutorial.converter.CategoryConverter;
import com.staxrt.tutorial.converter.QuestionConverter;
import com.staxrt.tutorial.dto.QuestionSearchCategoryDTO;
import com.staxrt.tutorial.dto.QuestionSearchDTO;
import com.staxrt.tutorial.dto.QuestionSearchFilterDTO;
import com.staxrt.tutorial.entity.CategoryEntity;
import com.staxrt.tutorial.entity.QuestionEntity;
import com.staxrt.tutorial.repository.QuestionRepository;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionsSearchService {

    @Autowired
    QuestionsService questionsService;

    @Autowired
    QuestionRepository questionRepository;
    
    @Autowired
    QuestionConverter questionConverter;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryConverter categoryConverter;

    public List<QuestionSearchDTO> getSearchResult(QuestionSearchFilterDTO filter) {
        List<QuestionSearchDTO> result = new ArrayList<>();
        List<QuestionEntity> questions = filterQuestions(questionRepository.findAll(), filter);

        
        return questions.stream()
                .map(questionConverter::convertToQuestionSearchDTO)
                .collect(Collectors.toList());
    }

    private List<QuestionEntity> filterQuestions(List<QuestionEntity> all, QuestionSearchFilterDTO filter) {
        throw new NotYetImplementedException("Crear filtro");
        //return null;
    }

    public List<QuestionSearchCategoryDTO> getCategoriesSearch() {

        return categoryConverter.convertToDTOList(categoryService.getAllCategories());
    }
}
