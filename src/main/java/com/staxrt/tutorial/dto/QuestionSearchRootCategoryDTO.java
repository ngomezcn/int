package com.staxrt.tutorial.dto;

import java.util.List;

public class QuestionSearchRootCategoryDTO {
    Long id;
    String name;
    List<CategoryDropdownDTO> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryDropdownDTO> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryDropdownDTO> children) {
        this.children = children;
    }
}
