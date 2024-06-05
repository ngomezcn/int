package com.staxrt.tutorial.repository;

import com.staxrt.tutorial.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity getByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

    UserEntity findByemailAndPassword(String email, String password);

    Optional<UserEntity> findByUsername(String username);

    UserEntity getByUsername(String username);


}
