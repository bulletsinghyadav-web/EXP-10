package com.hostelmanagement.hostel.controller;

import com.hostelmanagement.hostel.model.Room;
import com.hostelmanagement.hostel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    // Show the form to add a new room
    @GetMapping("/rooms/new")
    public String showAddRoomForm(Model model) {
        model.addAttribute("room", new Room());
        return "add-room";
    }

    // Save the new room to the database
    @PostMapping("/rooms")
    public String addRoom(Room room) {
        // Default status to "Vacant" if not set
        if (room.getStatus() == null || room.getStatus().isEmpty()) {
            room.setStatus("Vacant");
        }
        roomRepository.save(room);
        return "redirect:/rooms";
    }

    // Show the list of all rooms
    @GetMapping("/rooms")
    public String listRooms(Model model) {
        List<Room> rooms = roomRepository.findAll();
        model.addAttribute("rooms", rooms);
        return "rooms-list";
    }
}

