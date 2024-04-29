package com.staxrt.tutorial.repository;

import com.staxrt.tutorial.model.EmailVerification;
import com.staxrt.tutorial.model.ResetPassword;
import com.staxrt.tutorial.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResetPasswordRepository extends JpaRepository<ResetPassword, Long> {
    List<ResetPassword> findByEmailAddressAndCode(String emailAddress,String code);
}
