package com.staxrt.tutorial.dto;

import com.staxrt.tutorial.dto.entities.UserDTO;

public class AuthUserDTO extends UserDTO {

    public String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
