package com.hostelmanagement.hostel.repository;

import com.hostelmanagement.hostel.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Find students who don't have a room assigned
    List<Student> findByRoomIsNull();
}

