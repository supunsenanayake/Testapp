package com.example.demo.model;


import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "student_course")
public class StudentCourse {

    @EmbeddedId
    private StudentCourseId id;

    @Column(name = "grade")
    private String grade;

    // Default constructor
    public StudentCourse() {}

    // Parameterized constructor
    public StudentCourse(StudentCourseId id, String grade) {
        this.id = id;
        this.grade = grade;
    }

}