package com.staxrt.tutorial.entity.question;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.staxrt.tutorial.entity.AuditableEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "question")
@EntityListeners(AuditingEntityListener.class)
public class QuestionEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "media_path", nullable = true)
    private String mediaPath;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "question_settings_id", nullable = false)
    private QuestionSettingsEntity questionSettingsEntity;

    @ManyToMany
    @JoinTable(
            name = "question_categories",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<CategoryEntity> categories;;

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

    public QuestionSettingsEntity getQuestionSettings() {
        return questionSettingsEntity;
    }

    public void setQuestionSettings(QuestionSettingsEntity questionSettingsEntity) {
        this.questionSettingsEntity = questionSettingsEntity;
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
}