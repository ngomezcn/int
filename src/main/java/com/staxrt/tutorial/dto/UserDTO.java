package com.staxrt.tutorial.dto;

import com.staxrt.tutorial.model.User;

import javax.persistence.Column;

public class UserDTO  {

    public String firstName;
    public String lastName;
    public String emailAddress;
    public Boolean isTeacher;
    public Boolean isVerified;
}
