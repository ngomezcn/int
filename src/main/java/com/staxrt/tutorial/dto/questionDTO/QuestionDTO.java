package com.staxrt.tutorial.dto.questionDTO;

import com.staxrt.tutorial.dto.AnswerDTO;
import com.staxrt.tutorial.entity.question.AnswerEntity;

import java.util.List;

public class QuestionDTO {
    private long id;
    private String question;
    private List<CategoryDTO> categories;
    private List<AnswerDTO> answers;
    private QuestionOptionsDTO QuestionOptions;

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
        return QuestionOptions;
    }

    public void setQuestionOptions(QuestionOptionsDTO QuestionOptions) {
        this.QuestionOptions = QuestionOptions;
    }
}
