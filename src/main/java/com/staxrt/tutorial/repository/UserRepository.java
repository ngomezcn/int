package com.staxrt.tutorial.repository;

import com.staxrt.tutorial.model.EmailVerification;
import com.staxrt.tutorial.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmailAddress(String emailAddress);

    User findByEmailAddressAndPassword(String emailAddress, String password);

    Optional<User> findByUsername(String username);
}
