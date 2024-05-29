package com.staxrt.tutorial.dto;

import java.util.List;

public class QuestionSearchCategoryDTO {
    Long id;
    String name;
    List<QuestionSearchCategoryDTO> children;

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

    public List<QuestionSearchCategoryDTO> getChildren() {
        return children;
    }

    public void setChildren(List<QuestionSearchCategoryDTO> children) {
        this.children = children;
    }
}
