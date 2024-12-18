package com.example.assignment.Service;

import com.example.assignment.Model.Subject;
import com.example.assignment.Repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public Subject addSubject(Subject subject) {
        if(subjectRepository.findByName(subject.getName()) != null){
            throw new IllegalArgumentException("Subject with the same name already exists");
        }
        return subjectRepository.save(subject);
    }

    public List<Subject> getAll() {
        return subjectRepository.findAll();
    }

    public Subject findSubjectByName(String name) {
        return subjectRepository.findByName(name);
    }

    public void saveSubject(Subject subject) {
        subjectRepository.save(subject);
    }
}
