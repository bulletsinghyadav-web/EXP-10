package com.hostelmanagement.hostel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    // Show the admin dashboard (homepage)
    @GetMapping({"/", "/admin"})
    public String showDashboard() {
        return "admin-dashboard";
    }
}

