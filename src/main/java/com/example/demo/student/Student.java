package com.example.demo.student;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table
public class Student {

    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy =  GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;
    private String Name;
    private Integer Age;
    private LocalDate Dob;
    private String Email;

    public Student() {
    }

    public Student(Long id, String name, Integer age, LocalDate dob, String email) {
        this.id = id;
        Name = name;
        Age = age;
        Dob = dob;
        Email = email;
    }

    public Student(String name, Integer age, LocalDate dob, String email) {
        Name = name;
        Age = age;
        Dob = dob;
        Email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getAge() {
        return Age;
    }

    public void setAge(Integer age) {
        Age = age;
    }

    public LocalDate getDob() {
        return Dob;
    }

    public void setDob(LocalDate dob) {
        Dob = dob;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", Age=" + Age +
                ", Dob=" + Dob +
                ", Email='" + Email + '\'' +
                '}';
    }
}
