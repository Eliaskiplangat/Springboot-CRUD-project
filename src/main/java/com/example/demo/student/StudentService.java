package com.example.demo.student;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentService {
    public List<Student> getStudents() {
        return List.of(
                new Student(
                        "Marriam",
                        "Marriam.j@gmail.com",
                        LocalDate.of(1999, 1, 1),
                        20
                )
        );
    }
}
