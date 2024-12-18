package com.example.assignment.Repository;

import com.example.assignment.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByName(String name);
    Student findByAddress(String address);

    Student findByEmail(String username);
}