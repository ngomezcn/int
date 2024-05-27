package com.staxrt.tutorial.entity;

import com.staxrt.tutorial.util.RandomCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "reset_password")
@EntityListeners(AuditingEntityListener.class)
public class ResetPasswordEntity {

    public ResetPasswordEntity(String email) {
        this.email = email;
        this.code = RandomCode.get();
    }

    public ResetPasswordEntity() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "code", nullable = false)
    private String code;

    public String getEmail() {
        return email;
    }

    public String getCode() {
        return code;
    }
}
