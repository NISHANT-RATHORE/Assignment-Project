package com.example.assignment.Service;

import com.example.assignment.Model.Student;
import com.example.assignment.Repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    public Student addStudent(Student student) {
        if((studentRepository.findByName(student.getName()) != null) && (studentRepository.findByAddress(student.getAddress()) != null)){
            log.warn("Student with the same name and address already exists");
            throw new IllegalArgumentException("Student with the same name and address already exists");
        }
        return studentRepository.save(student);
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }
}
