package com.staxrt.tutorial.repository;

import com.staxrt.tutorial.entity.ResetPasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResetPasswordRepository extends JpaRepository<ResetPasswordEntity, Long> {
    List<ResetPasswordEntity> findByEmailAndCode(String email, String code);

    void deleteByEmail(String email);

}
