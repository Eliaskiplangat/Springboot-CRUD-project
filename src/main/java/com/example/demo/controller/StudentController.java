package com.example.demo.controller;

import com.example.demo.dto.StudentRequest;
import com.example.demo.entity.Student;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.StudentService;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping
    public void registerNewStudent(
            @Valid @RequestBody StudentRequest request) {

        studentService.addNewStudent(request);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {

        studentService.updateStudent(studentId, name, email);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(
            @PathVariable("studentId") Long studentId) {

        studentService.deleteStudent(studentId);
    }
}