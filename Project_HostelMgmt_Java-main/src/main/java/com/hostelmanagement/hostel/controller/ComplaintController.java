package com.hostelmanagement.hostel.controller;

import com.hostelmanagement.hostel.model.Complaint;
import com.hostelmanagement.hostel.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ComplaintController {

    @Autowired
    private ComplaintRepository complaintRepository;

    // Show the form to submit a complaint (public page for students)
    @GetMapping("/complaint")
    public String showComplaintForm(Model model) {
        model.addAttribute("complaint", new Complaint());
        return "submit-complaint";
    }

    // Save the complaint to the database
    @PostMapping("/complaint")
    public String submitComplaint(Complaint complaint) {
        complaintRepository.save(complaint);
        return "redirect:/complaint?success";
    }

    // Show all complaints (admin page)
    @GetMapping("/admin/complaints")
    public String listComplaints(Model model) {
        List<Complaint> complaints = complaintRepository.findAll();
        model.addAttribute("complaints", complaints);
        return "complaints-list";
    }
}

