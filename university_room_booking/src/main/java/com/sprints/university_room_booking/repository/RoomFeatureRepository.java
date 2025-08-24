package com.sprints.university_room_booking.repository;

import com.sprints.university_room_booking.model.RoomFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomFeatureRepository extends JpaRepository<RoomFeature, Long>
{

}
