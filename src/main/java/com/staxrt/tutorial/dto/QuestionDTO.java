package com.staxrt.tutorial.dto;

import java.util.List;

public class QuestionDTO {
    private long id;
    private String question;
    private String mediaPath;
    private List<CategoryDTO> categories;
    private List<AnswerDTO> answers;
    private QuestionOptionsDTO questionOptions;
    private ScoreByLevelDTO scoreByLevel;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }

    public QuestionOptionsDTO getQuestionOptions() {
        return questionOptions;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public void setQuestionOptions(QuestionOptionsDTO QuestionOptions) {
        this.questionOptions = QuestionOptions;
    }

    public ScoreByLevelDTO getScoreByLevel() {
        return scoreByLevel;
    }

    public void setScoreByLevel(ScoreByLevelDTO scoreByLevel) {
        this.scoreByLevel = scoreByLevel;
    }
}
