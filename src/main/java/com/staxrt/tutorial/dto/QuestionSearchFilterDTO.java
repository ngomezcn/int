package com.staxrt.tutorial.dto;

import com.staxrt.tutorial.dto.entities.CategoryDTO;
import com.staxrt.tutorial.enums.AnswerType;

import java.util.Date;
import java.util.List;

public class QuestionSearchFilterDTO {

    String question;
    Date startDate;
    Date endDate;
    Boolean flagged;
    List<String> createdBy;
    List<AnswerType> answerTypeList;
    List<CategoryDTO> categories;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getFlagged() {
        return flagged;
    }

    public void setFlagged(Boolean flagged) {
        this.flagged = flagged;
    }

    public List<String> getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(List<String> createdBy) {
        this.createdBy = createdBy;
    }

    public List<AnswerType> getAnswerTypeList() {
        return answerTypeList;
    }

    public void setAnswerTypeList(List<AnswerType> answerTypeList) {
        this.answerTypeList = answerTypeList;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }
}
