package com.staxrt.tutorial.repository;


import com.staxrt.tutorial.entity.EmailVerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerificationEntity, Long> {

    List<EmailVerificationEntity> findByEmailAndCode(String email, String code);

    void deleteByEmail(String email);
}
