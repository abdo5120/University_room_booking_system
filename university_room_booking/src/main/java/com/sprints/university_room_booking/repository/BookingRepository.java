package com.sprints.university_room_booking.repository;

import com.sprints.university_room_booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>
{
    @Query("""
        SELECT b FROM Booking b
        WHERE b.room.id = :roomId
        AND (b.startTime <= :endDate AND b.endTime >= :startDate)""")
    List<Booking> findOverlappingBookings(@Param("roomId") Long roomId,
                                          @Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate);

}
