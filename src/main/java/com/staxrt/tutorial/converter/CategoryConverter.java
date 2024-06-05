package com.staxrt.tutorial.converter;

import com.staxrt.tutorial.dto.CategoryDropdownDTO;
import com.staxrt.tutorial.dto.entities.CategoryDTO;
import com.staxrt.tutorial.entity.CategoryEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryConverter {

    public CategoryDTO convertToDTO(CategoryEntity category) {
        CategoryDTO dto = new CategoryDTO();

        dto.setId(category.getId());
        dto.setName(category.getName());

        return dto;
    }

    public CategoryDropdownDTO convertToQuestionSearchDTO(CategoryEntity categoryEntity) {
        CategoryDropdownDTO dto = new CategoryDropdownDTO();
        dto.setId(categoryEntity.getId());
        dto.setName(categoryEntity.getName());

        if (categoryEntity.getChildren() != null) {
            List<CategoryDropdownDTO> childrenDTO = categoryEntity.getChildren().stream()
                    .map(this::convertToQuestionSearchDTO)
                    .collect(Collectors.toList());
            dto.setChildren(childrenDTO);
        }

        return dto;
    }


    public List<CategoryDropdownDTO> convertToDTOList(List<CategoryEntity> categoryEntities) {
        List<CategoryDropdownDTO> dtos = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryEntities) {
            if (categoryEntity.getParents().isEmpty()) {
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
