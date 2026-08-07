package com.example.demo.controller;

import com.example.demo.dto.StudentRequest;
import com.example.demo.dto.StudentResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/search")
    public List<StudentResponse> searchStudents(
            @RequestParam String name) {

        return studentService.searchStudents(name);
    }
    @GetMapping
    public ResponseEntity<Page<StudentResponse>> getStudents(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                studentService.getStudents(page, size, sortBy, direction)
        );
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<String> registerNewStudent(
            @Valid @RequestBody StudentRequest request) {

        studentService.addNewStudent(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Student created successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{studentId}")
    public ResponseEntity<String> updateStudent(
            @PathVariable Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {

        studentService.updateStudent(studentId, name, email);

        return ResponseEntity.ok("Student updated successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{studentId}")
    public ResponseEntity<String> deleteStudent(
            @PathVariable Long studentId) {

        studentService.deleteStudent(studentId);

        return ResponseEntity.ok("Student deleted successfully");
    }
}