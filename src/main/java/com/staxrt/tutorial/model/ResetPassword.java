package com.staxrt.tutorial.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "reset_password")
@EntityListeners(AuditingEntityListener.class)
public class ResetPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "email_address", nullable = false)
    private String emailAddress;

    @Column(name = "code", nullable = false)
    private String code;

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getCode() {
        return code;
    }
}
