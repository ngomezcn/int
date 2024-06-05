package com.staxrt.tutorial.converter;

import com.staxrt.tutorial.dto.entities.UserDTO;
import com.staxrt.tutorial.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserDTO convertToDTO(UserEntity user) {
        UserDTO dto = new UserDTO();

        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());

        return dto;
    }
}
