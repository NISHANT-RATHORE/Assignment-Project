package com.example.assignment.Mapper;

import com.example.assignment.DTO.LoginRequest;
import com.example.assignment.Model.Student;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LoginMapper {
    public static Student loginToUser(LoginRequest request) {
        return Student.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }
}