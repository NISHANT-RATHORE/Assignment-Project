package com.example.assignment.Controller;

import com.example.assignment.DTO.LoginRequest;
import com.example.assignment.Model.Student;
import com.example.assignment.Model.Subject;
import com.example.assignment.Service.StudentService;
import com.example.assignment.Util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public StudentController(StudentService studentService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.studentService = studentService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        try{
            log.info("Registering student....");
            Student savedStudent  = studentService.addStudent(student);
            log.info("student registered !");
            return ResponseEntity.ok(savedStudent);
        } catch (Exception e){
            log.error("error occured in registering...");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginStudent(@RequestBody LoginRequest request) {
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = studentService.loadUserByUsername(request.getEmail());
            String token = jwtUtil.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(token);
        } catch (Exception e){
            log.error("error occured in logging in...");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Student>> getAllStudents() {
        try{
            log.info("getting all students...");
            List<Student> allStudents = studentService.getAll();
            log.info("getting all students done!");
            return ResponseEntity.ok(allStudents);
        } catch (Exception e){
            log.error("error occured in registering...");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/registerSubject")
    public ResponseEntity<Student> registerSubject(@RequestParam(name = "subName") String subName, @RequestHeader("Authorization") String token) {
        try {
            String email = jwtUtil.extractUsername(token.substring(7)); // Remove "Bearer " prefix
            Student student = studentService.findStudentByEmail(email); // Use a method to find the student by email
            Student updatedStudent = studentService.registerSubject(student.getEmail(), subName);
            return ResponseEntity.ok(updatedStudent);
        } catch (Exception e) {
            log.error("Error occurred in registering subject...", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}