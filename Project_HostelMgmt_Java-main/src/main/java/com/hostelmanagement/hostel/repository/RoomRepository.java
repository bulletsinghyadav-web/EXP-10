package com.hostelmanagement.hostel.repository;

import com.hostelmanagement.hostel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    // Find vacant rooms
    List<Room> findByStatus(String status);
}

