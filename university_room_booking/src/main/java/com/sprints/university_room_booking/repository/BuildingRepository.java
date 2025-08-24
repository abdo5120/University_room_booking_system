package com.sprints.university_room_booking.repository;

import com.sprints.university_room_booking.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long>
{
}