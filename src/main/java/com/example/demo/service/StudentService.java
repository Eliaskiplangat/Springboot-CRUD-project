package com.example.demo.service;

import com.example.demo.dto.StudentRequest;
import com.example.demo.dto.StudentResponse;
import com.example.demo.entity.Student;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.demo.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class StudentService {

    private static final Logger logger =
            LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Page<StudentResponse> getStudents(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return studentRepository.findAll(pageable)
                .map(student -> new StudentResponse(
                        student.getId(),
                        student.getName(),
                        student.getEmail(),
                        student.getAge()
                ));
    }

    public void addNewStudent(StudentRequest request) {

        Optional<Student> studentByEmail =
                studentRepository.findStudentByEmail(request.getEmail());

        if (studentByEmail.isPresent()) {
            throw new IllegalStateException("Email already taken");
        }

        Student student = new Student();

        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setDob(request.getDob());
        student.setAge(request.getAge());

        logger.info("Adding student {}", student.getEmail());

        studentRepository.save(student);
    }

    @Transactional
    public void updateStudent(Long studentId,
                              String name,
                              String email) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Student with id " + studentId + " does not exist"));

        if (name != null &&
                !name.isEmpty() &&
                !name.equals(student.getName())) {

            student.setName(name);
        }

        if (email != null &&
                !email.isEmpty() &&
                !email.equals(student.getEmail())) {

            student.setEmail(email);
        }

        logger.info("Updated student {}", studentId);
    }

    public void deleteStudent(Long studentId) {

        boolean exists = studentRepository.existsById(studentId);

        if (!exists) {
            throw new IllegalStateException(
                    "Student with id " + studentId + " does not exist");
        }

        logger.warn("Deleting student {}", studentId);

        studentRepository.deleteById(studentId);
    }
}