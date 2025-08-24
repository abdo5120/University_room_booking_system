package com.sprints.university_room_booking.service;

import com.sprints.university_room_booking.dto.BookingHistoryDto;
import com.sprints.university_room_booking.mapper.BookingHistoryMapper;
import com.sprints.university_room_booking.model.BookingHistory;
import com.sprints.university_room_booking.repository.BookingHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingHistoryService
{
    private final BookingHistoryRepository bookingHistoryRepository;
    private final BookingHistoryMapper bookingHistoryMapper;

    public List<BookingHistoryDto> getAllHistory()
    {
        if(bookingHistoryRepository.findAll().isEmpty())
            throw new RuntimeException("No booking history found");

        List<BookingHistory> bookingHistoryList = bookingHistoryRepository.findAll();
        return bookingHistoryMapper.toDTOList(bookingHistoryList);
    }

    public BookingHistoryDto getHistoryById(Long id)
    {
        BookingHistory bookingHistory = bookingHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No booking history found"));

        return bookingHistoryMapper.toDTO(bookingHistory);
    }
}
