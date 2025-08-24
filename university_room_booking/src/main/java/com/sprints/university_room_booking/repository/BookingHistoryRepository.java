package com.sprints.university_room_booking.repository;

import com.sprints.university_room_booking.model.BookingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingHistoryRepository extends JpaRepository<BookingHistory, Long>
{
    List<BookingHistory> findByBooking_id(long bookingId);
}
