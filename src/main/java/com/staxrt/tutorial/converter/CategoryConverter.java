package com.staxrt.tutorial.converter;

import com.staxrt.tutorial.dto.AddCategoryDTO;
import com.staxrt.tutorial.dto.QuestionSearchCategoryDTO;
import com.staxrt.tutorial.dto.QuestionSearchRootCategoryDTO;
import com.staxrt.tutorial.dto.entities.CategoryDTO;
import com.staxrt.tutorial.entity.CategoryEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryConverter {

    public CategoryDTO convertToDTO(CategoryEntity category)
    {
        CategoryDTO dto = new CategoryDTO();

        dto.setId(category.getId());
        dto.setName(category.getName());

        return dto;
    }

    public QuestionSearchCategoryDTO convertToQuestionSearchDTO(CategoryEntity categoryEntity) {
        QuestionSearchCategoryDTO dto = new QuestionSearchCategoryDTO();
        dto.setId(categoryEntity.getId());
        dto.setName(categoryEntity.getName());

        if (categoryEntity.getChildren() != null) {
            List<QuestionSearchCategoryDTO> childrenDTO = categoryEntity.getChildren().stream()
                    .map(this::convertToQuestionSearchDTO)
                    .collect(Collectors.toList());
            dto.setChildren(childrenDTO);
        }

        return dto;
    }


    public List<QuestionSearchCategoryDTO> convertToDTOList(List<CategoryEntity> categoryEntities) {
        List<QuestionSearchCategoryDTO> dtos = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryEntities) {
            if(categoryEntity.getParents().isEmpty())
            {
                dtos.add(convertToQuestionSearchDTO(categoryEntity));
            }
        }
        return dtos;
    }

    public CategoryEntity convertToEntity(String name) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(name);

        return categoryEntity;
    }
}
