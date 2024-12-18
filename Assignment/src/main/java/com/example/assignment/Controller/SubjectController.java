package com.example.assignment.Controller;

import com.example.assignment.Model.Subject;
import com.example.assignment.Service.SubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/create")
    public ResponseEntity<Subject> createSubject(@RequestBody Subject subject) {
        try {
            log.info("Creating subject....");
            Subject savedSubject = subjectService.addSubject(subject);
            log.info("Subject created!");
            return ResponseEntity.ok(savedSubject);
        } catch (Exception e) {
            log.error("Error occurred in creating subject...");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Subject>> getAllSubjects() {
        try{
            log.info("getting all subjects...");
            List<Subject> allSubjects = subjectService.getAll();
            log.info("getting all subjects done!");
            return ResponseEntity.ok(allSubjects);
        } catch (Exception e){
            log.error("error occured in registering...");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}