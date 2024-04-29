package com.staxrt.tutorial.model;

import com.staxrt.tutorial.util.RandomCode;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Random;


@Entity
@Table(name = "email_verification")
@EntityListeners(AuditingEntityListener.class)
public class EmailVerification {

    public EmailVerification() {

    }

    public EmailVerification(String emailAddress) {
        this.emailAddress = emailAddress;
        this.code = RandomCode.get();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "email_address", nullable = false)
    private String emailAddress;

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

    public String getEmailAddress() {
        return emailAddress;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


}
