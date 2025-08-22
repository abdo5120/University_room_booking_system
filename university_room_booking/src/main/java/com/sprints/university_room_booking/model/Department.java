package com.sprints.university_room_booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // e.g., "Computer Science", "Physics"

    // A department can have many users
    @OneToMany(mappedBy = "department")
    private List<User> users;

    // A department can own/manage many rooms
    @OneToMany(mappedBy = "department")
    private List<Room> rooms;
}
