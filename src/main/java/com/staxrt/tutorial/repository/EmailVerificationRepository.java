package com.staxrt.tutorial.repository;


import com.staxrt.tutorial.model.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.Query;
import java.util.List;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {

    List<EmailVerification> findByEmailAddressAndCode(String emailAddress, String code);

    void deleteByEmailAddress(String emailAddress);
}
