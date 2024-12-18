package com.example.assignment.Configuration;

import com.example.assignment.Model.Student;
import com.example.assignment.Repository.StudentRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataInitializer {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        if (studentRepository.findByEmail("admin@example.com") == null) {
            Student admin = Student.builder()
                    .name("Admin")
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("adminpassword"))
                    .address("Admin Address")
                    .roles(List.of("ADMIN"))
                    .build();
            studentRepository.save(admin);
        }
    }
}