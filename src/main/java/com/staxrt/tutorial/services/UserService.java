package com.staxrt.tutorial.services;

import com.staxrt.tutorial.converter.UserConverter;
import com.staxrt.tutorial.dto.entities.UserDTO;
import com.staxrt.tutorial.entity.UserEntity;
import com.staxrt.tutorial.enums.UserRole;
import com.staxrt.tutorial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserConverter userConverter;

    public List<UserDTO> getTeacherRoster() {

        List<UserEntity> teachers = userRepository.findAll()
                .stream()
                .filter(user -> user.getRole() == UserRole.TEACHER)
                .collect(Collectors.toList());

        return teachers.stream()
                .map(userConverter::convertToDTO)
                .collect(Collectors.toList());

    }
}
