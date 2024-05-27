package com.staxrt.tutorial.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "answer")
@EntityListeners(AuditingEntityListener.class)
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "number", nullable = true)
    private String number;

    @Column(name = "answer", nullable = false)
    private String answer;

    @Column(name = "is_correct", nullable = false)
    private Boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity questionEntity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public String getAnswer() {
        return answer;
    }

    public String getNumber() {
        return number;
    }

    public QuestionEntity getQuestion() {
        return questionEntity;
    }

    public void setQuestion(QuestionEntity questionEntity) {
        this.questionEntity = questionEntity;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }
}
