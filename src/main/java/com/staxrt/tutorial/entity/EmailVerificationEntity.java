package com.staxrt.tutorial.entity;

import com.staxrt.tutorial.util.RandomCode;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "email_verification")
@EntityListeners(AuditingEntityListener.class)
public class EmailVerificationEntity {

    public EmailVerificationEntity() {

    }

    public EmailVerificationEntity(String email) {
        this.email = email;
        this.code = RandomCode.get();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "code", nullable = false)
    private String code;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getCode() {
        return code;
    }

    public String getemail() {
        return email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


}
