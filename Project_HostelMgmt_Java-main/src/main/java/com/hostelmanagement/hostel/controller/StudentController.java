package com.hostelmanagement.hostel.controller;

import com.hostelmanagement.hostel.model.Student;
import com.hostelmanagement.hostel.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    // Show the form to register a new student
    @GetMapping("/students/new")
    public String showRegisterForm(Model model) {
        model.addAttribute("student", new Student());
        return "register-student";
    }

    // Save the new student to the database
    @PostMapping("/students")
    public String registerStudent(Student student) {
        studentRepository.save(student);
        return "redirect:/students";
    }

    // Show the list of all students
    @GetMapping("/students")
    public String listStudents(Model model) {
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "students-list";
    }
}

