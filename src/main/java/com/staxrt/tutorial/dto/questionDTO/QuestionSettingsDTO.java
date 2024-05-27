package com.staxrt.tutorial.dto.questionDTO;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class QuestionSettingsDTO {
    private long id;

    private String answerType;

    private String timeLimit;

    public long getId() {
        return id;
    }

    public String getAnswerType() {
        return answerType;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }
}
