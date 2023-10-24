package com.example.authenticationspring.model.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDto {
    private int userId;
    private String firstName;
    private String dob;
    private String gender;
    private String phone;
    private String email;
    private String password;
    private String createdDate;
    private String lastName;
    private String address;
}