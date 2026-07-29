package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository  studentRepository;
   @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
       return studentRepository.findAll();
    }
    public void addNewStudent(Student student) {
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
    }
    public void deleteStudent(Long studentId) {

        boolean exists = studentRepository.existsById(studentId);

        if (!exists) {
            throw new IllegalStateException(
                    "Student with id " + studentId + " does not exist");
        }

        studentRepository.deleteById(studentId);
    }
}
