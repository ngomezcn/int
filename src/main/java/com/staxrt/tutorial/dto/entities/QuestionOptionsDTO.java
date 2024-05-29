package com.staxrt.tutorial.dto.entities;

import com.staxrt.tutorial.enums.AnswerType;

public class QuestionOptionsDTO {
    private long id;

    private AnswerType answerType;

    private Integer timeLimit;

    public long getId() {
        return id;
    }

    public AnswerType getAnswerType() {
        return answerType;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAnswerType(AnswerType answerType) {
        this.answerType = answerType;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }
}
