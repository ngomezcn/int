package com.staxrt.tutorial.dto;

import java.util.Date;
import java.util.List;

public class QuestionSearchFilterDTO {

    String searchValue;
    Date startDate;
    Date endDate;
    Boolean flagged;
    List<String> createdByList;
    List<String> answerTypeList;
    List<String> categories;

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
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

    public List<String> getCreatedByList() {
        return createdByList;
    }

    public void setCreatedByList(List<String> createdByList) {
        this.createdByList = createdByList;
    }

    public List<String> getAnswerTypeList() {
        return answerTypeList;
    }

    public void setAnswerTypeList(List<String> answerTypeList) {
        this.answerTypeList = answerTypeList;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
