package com.sprints.university_room_booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, unique = true, nullable = false)
    private String name; // e.g., ROLE_STUDENT, ROLE_FACULTY, ROLE_ADMIN

}