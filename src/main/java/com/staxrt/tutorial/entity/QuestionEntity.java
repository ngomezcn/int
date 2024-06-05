package com.staxrt.tutorial.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "question")
@EntityListeners(AuditingEntityListener.class)
public class QuestionEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "media_path", nullable = true)
    private String mediaPath;

    @Column(name = "flagged", nullable = false)
    private boolean flagged = false;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "question_options_id", nullable = false)
    private QuestionOptionsEntity QuestionOptionsEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "score_by_level_id")
    private ScoreByLevelEntity scoreByLevel;

    @ManyToMany
    @JoinTable(
            name = "question_categories",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<CategoryEntity> categories;
    ;

    @OneToMany(mappedBy = "questionEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerEntity> answerEntities;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public com.staxrt.tutorial.entity.QuestionOptionsEntity getQuestionOptionsEntity() {
        return QuestionOptionsEntity;
    }

    public void setQuestionOptionsEntity(com.staxrt.tutorial.entity.QuestionOptionsEntity questionOptionsEntity) {
        QuestionOptionsEntity = questionOptionsEntity;
    }

    public ScoreByLevelEntity getScoreByLevel() {
        return scoreByLevel;
    }

    public void setScoreByLevel(ScoreByLevelEntity scoreByLevel) {
        this.scoreByLevel = scoreByLevel;
    }

    public List<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryEntity> categories) {
        this.categories = categories;
    }

    public List<AnswerEntity> getAnswerEntities() {
        return answerEntities;
    }

    public void setAnswerEntities(List<AnswerEntity> answerEntities) {
        this.answerEntities = answerEntities;
    }



    /*public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public QuestionOptionsEntity getQuestionOptions() {
        return QuestionOptionsEntity;
    }

    public void setQuestionOptions(QuestionOptionsEntity QuestionOptionsEntity) {
        this.QuestionOptionsEntity = QuestionOptionsEntity;
    }

    public List<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryEntity> categories) {
        this.categories = categories;
    }

    public List<AnswerEntity> getAnswers() {
        return answerEntities;
    }

    public void setAnswers(List<AnswerEntity> answerEntities) {
        this.answerEntities = answerEntities;
    }

    public com.staxrt.tutorial.entity.QuestionOptionsEntity getQuestionOptionsEntity() {
        return QuestionOptionsEntity;
    }

    public ScoreByLevelEntity getScoreByLevel() {
        return scoreByLevel;
    }

    public List<AnswerEntity> getAnswerEntities() {
        return answerEntities;
    }

    public void setQuestionOptionsEntity(com.staxrt.tutorial.entity.QuestionOptionsEntity questionOptionsEntity) {
        QuestionOptionsEntity = questionOptionsEntity;
    }

    public void setScoreByLevel(ScoreByLevelEntity scoreByLevel) {
        this.scoreByLevel = scoreByLevel;
    }

    public void setAnswerEntities(List<AnswerEntity> answerEntities) {
        this.answerEntities = answerEntities;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }*/
}