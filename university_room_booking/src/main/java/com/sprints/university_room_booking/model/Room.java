package com.sprints.university_room_booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomNumber; // e.g., "101", "Lab A"

    @Column(nullable = false)
    private Integer capacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    @ToString.Exclude // To prevent recursion in logs
    private Building building;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "room_to_features",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    @ToString.Exclude
    private Set<RoomFeature> features = new HashSet<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Booking> bookings;

    // A room can be owned by a department (or null if it's a general room)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id") // This column can be nullable
    @ToString.Exclude
    private Department department;
}
