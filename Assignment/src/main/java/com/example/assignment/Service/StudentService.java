package com.example.assignment.Service;

import com.example.assignment.DTO.LoginRequest;
import com.example.assignment.Mapper.LoginMapper;
import com.example.assignment.Model.Student;
import com.example.assignment.Repository.StudentRepository;
import com.example.assignment.Util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StudentService implements UserDetailsService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public StudentService(StudentRepository studentRepository, PasswordEncoder passwordEncoder, @Lazy AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public Student addStudent(Student student) {
        if((studentRepository.findByName(student.getName()) != null) && (studentRepository.findByAddress(student.getAddress()) != null)){
            log.warn("Student with the same name and address already exists");
            throw new IllegalArgumentException("Student with the same name and address already exists");
        }
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        student.setRoles(List.of("STUDENT"));
        return studentRepository.save(student);
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByEmail(username);
        if (student != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(student.getEmail())
                    .password(student.getPassword())
                    .roles(student.getRoles().toArray(new String[0]))
                    .build();
        }
        throw new UsernameNotFoundException(username.concat(" user not found"));
    }

   public String loginStudent(LoginRequest request) {
        try {
            Student student = LoginMapper.loginToUser(request);
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(student.getEmail(), student.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = loadUserByUsername(student.getEmail());
            String token = jwtUtil.generateToken(userDetails.getUsername());
            log.info("Student {} logged in successfully", student.getEmail());
            return token;
        } catch (Exception e) {
            log.error("Error logging in student: ", e);
            throw new RuntimeException("Login failed", e);
        }
    }
}