package com.staxrt.tutorial.services;

import com.staxrt.tutorial.converter.CategoryConverter;
import com.staxrt.tutorial.converter.QuestionConverter;
import com.staxrt.tutorial.dto.CategoryDropdownDTO;
import com.staxrt.tutorial.dto.QuestionSearchResultDTO;
import com.staxrt.tutorial.dto.QuestionSearchFilterDTO;
import com.staxrt.tutorial.entity.QuestionEntity;
import com.staxrt.tutorial.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    public static List<CategoryDropdownDTO> convertCategoryIds(List<CategoryDropdownDTO> categories, String parentId) {
        for (CategoryDropdownDTO category : categories) {
            String newId = parentId.isEmpty() ? category.getName() : parentId + "-" + category.getName();
            newId = newId.replace(" ", "%");
            category.setHierarchicalId(newId);
            if (category.getChildren() != null && !category.getChildren().isEmpty()) {
                convertCategoryIds(category.getChildren(), newId);
            }
        }

        return categories;
    }

    public List<QuestionSearchResultDTO> getSearchResult(Optional<QuestionSearchFilterDTO> filter) {

        if (filter.isPresent()) {
            List<QuestionEntity> filteredQuestions = filterQuestions(questionRepository.findAll(), filter.get());

            return filteredQuestions.stream()
                    .map(questionConverter::convertToQuestionSearchDTO).collect(Collectors.toList());
        } else {
            return questionRepository.findAll().stream()
                    .map(questionConverter::convertToQuestionSearchDTO).collect(Collectors.toList());
        }
    }

    /*private List<QuestionEntity> filterQuestions(List<QuestionEntity> allQuestions, QuestionSearchFilterDTO filter) {
        List<QuestionEntity> result = new ArrayList<>();

        for (QuestionEntity question : allQuestions) {

            if (question.getQuestion().toLowerCase().contains(filter.getQuestion()) || filter.getQuestion().isEmpty()) {
                result.add(question);
            }

            if(filter.getCategories().isEmpty())
            {

            }
        }
        return result;
    }*/

    private List<QuestionEntity> filterQuestions(List<QuestionEntity> questions, QuestionSearchFilterDTO filter) {
        List<QuestionEntity> filteredQuestions = new ArrayList<>();

        for (QuestionEntity question : questions) {
            boolean matches = true;

            if (filter.getSearchValue() != null && !question.getQuestion().contains(filter.getSearchValue())) {
                matches = false;
            }

            if (filter.getStartDate() != null && question.getCreatedAt().before(filter.getStartDate())) {
                matches = false;
            }

            if (filter.getEndDate() != null && question.getCreatedAt().after(filter.getEndDate())) {
                matches = false;
            }

            if (filter.getCreatedByList() != null && !filter.getCreatedByList().isEmpty() && !filter.getCreatedByList().contains(question.getCreatedBy())) {
                matches = false;
            }

            if (filter.getAnswerTypeList() != null && !filter.getAnswerTypeList().isEmpty() && !filter.getAnswerTypeList().contains(question.getQuestionOptionsEntity().getAnswerType().toString())) {
                matches = false;
            }

            if (filter.getFlagged() && filter.getFlagged() != question.isFlagged()) {
                matches = false;
            }

            /*if (filter.getCategories() != null && !filter.getCategories().isEmpty()) {
                boolean categoryMatch = false;
                for (CategoryDTO category : question.getCategories()) {
                    if (filter.getCategories().contains(category.getName())) {
                        categoryMatch = true;
                        break;
                    }
                }
                if (!categoryMatch) {
                    matches = false;
                }
            }*/

            if (matches) {
                filteredQuestions.add(question);
            }
        }

        return filteredQuestions;
    }

    public List<CategoryDropdownDTO> getCategoriesSearch() {

        List<CategoryDropdownDTO> categories = categoryConverter.convertToDTOList(categoryService.getAllCategories());

        /*long id = 0;
        for (QuestionSearchCategoryDTO category : categories) {
            category.setId(id);
            id++;
        }*/

        return convertCategoryIds(categories, "");
    }


}
