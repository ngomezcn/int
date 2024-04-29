package com.staxrt.tutorial.dto;

import com.staxrt.tutorial.model.User;

import javax.persistence.Column;

public class UserDTO  {

    public String username;
    public String emailAddress;
    public Boolean isTeacher;
    public Boolean isVerified;
}
