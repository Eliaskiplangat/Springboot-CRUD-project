package com.example.demo.student;

import java.time.LocalDate;
import java.util.List;

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
