package com.staxrt.tutorial.dto.questionDTO;

public class QuestionOptionsDTO {
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
