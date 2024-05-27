package com.staxrt.tutorial.dto.authDTOS;

public class LoginResponseDTO {
    public String displayName;
    public String email;
    public String authorization;

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getAuthorization() {
        return authorization;
    }
}
