package com.example.assignment.Service;

import com.example.assignment.Model.Student;
import com.example.assignment.Model.Subject;
import com.example.assignment.Repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final SubjectService subjectService;

    @Autowired
    public StudentService(StudentRepository studentRepository, PasswordEncoder passwordEncoder, SubjectService subjectService) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.subjectService = subjectService;
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

    public Student registerSubject(String email, String subName) {
        Student student = studentRepository.findByEmail(email);
        if (student == null) {
            throw new IllegalArgumentException("Student not found");
        }
        Subject subject = subjectService.findSubjectByName(subName);
        if (subject == null || subject.getStudent() != null) {
            throw new IllegalArgumentException("Subject already registered to a student or not found");
        }
        subject.setStudent(student);
        student.getSubjects().add(subject);
        subjectService.saveSubject(subject);
        return studentRepository.save(student);
    }

    public Student findStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }
}