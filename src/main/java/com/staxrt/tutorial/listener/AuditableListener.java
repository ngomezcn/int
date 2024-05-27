package com.staxrt.tutorial.listener;

import com.staxrt.tutorial.entity.AuditableEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.PrePersist;

public class AuditableListener {

    @PrePersist
    public void setCreatedBy(AuditableEntity auditable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            auditable.setCreatedBy(username);
        } else {
            auditable.setCreatedBy("");
        }
    }
}