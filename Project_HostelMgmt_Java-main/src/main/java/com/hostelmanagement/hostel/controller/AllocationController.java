package com.hostelmanagement.hostel.controller;

import com.hostelmanagement.hostel.model.Room;
import com.hostelmanagement.hostel.model.Student;
import com.hostelmanagement.hostel.repository.RoomRepository;
import com.hostelmanagement.hostel.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AllocationController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoomRepository roomRepository;

    // Show the allocation form with unassigned students and vacant rooms
    @GetMapping("/allocate")
    public String showAllocationForm(Model model) {
        // Get students without a room
        List<Student> unassignedStudents = studentRepository.findByRoomIsNull();

        // Get vacant rooms
        List<Room> vacantRooms = roomRepository.findByStatus("Vacant");

        model.addAttribute("students", unassignedStudents);
        model.addAttribute("rooms", vacantRooms);

        return "allocate-room";
    }

    // Handle the room allocation
    @PostMapping("/allocate")
    public String allocateRoom(@RequestParam Long studentId, @RequestParam Long roomId) {
        // Find the student and room
        Student student = studentRepository.findById(studentId).orElse(null);
        Room room = roomRepository.findById(roomId).orElse(null);

        if (student != null && room != null) {
            // Link the student to the room
            student.setRoom(room);

            // Update room status to "Occupied"
            room.setStatus("Occupied");

            // Save both entities
            studentRepository.save(student);
            roomRepository.save(room);
        }

        return "redirect:/allocate";
    }
}

