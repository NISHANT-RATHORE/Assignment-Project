package com.example.assignment.Controller;

import com.example.assignment.Model.Student;
import com.example.assignment.Service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
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

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        try{
            List<Student> allStudents = studentService.getAll();
            log.info("getting all students...");
            return ResponseEntity.ok(allStudents);
        } catch (Exception e){
            log.error("error occured in registering...");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}