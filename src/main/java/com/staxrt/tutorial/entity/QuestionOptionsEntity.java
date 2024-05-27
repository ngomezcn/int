package com.staxrt.tutorial.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "question_options")
@EntityListeners(AuditingEntityListener.class)
public class QuestionOptionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "answer_type", nullable = false)
    private String answerType;

    @Column(name = "time_limit", nullable = false)
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