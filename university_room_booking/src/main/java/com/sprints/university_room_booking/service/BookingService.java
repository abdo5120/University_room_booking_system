package com.sprints.university_room_booking.service;

import com.sprints.university_room_booking.dto.BookingApprovalDto;
import com.sprints.university_room_booking.dto.BookingHistoryDto;
import com.sprints.university_room_booking.dto.BookingRequestDto;
import com.sprints.university_room_booking.dto.BookingResponseDto;
import com.sprints.university_room_booking.mapper.BookingHistoryMapper;
import com.sprints.university_room_booking.mapper.BookingMapper;
import com.sprints.university_room_booking.model.Booking;
import com.sprints.university_room_booking.model.BookingHistory;
import com.sprints.university_room_booking.model.BookingStatus;
import com.sprints.university_room_booking.repository.BookingHistoryRepository;
import com.sprints.university_room_booking.repository.BookingRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sprints.university_room_booking.model.BookingStatus.*;

@Service
@RequiredArgsConstructor
public class BookingService
{
    private final BookingRepository bookingRepository;
    private final BookingHistoryRepository bookingHistoryRepository;
    private final BookingMapper bookingMapper;
    private final BookingHistoryMapper bookingHistoryMapper;
    private final RoomService roomService;

    public BookingResponseDto createBooking(@Valid BookingRequestDto bookingRequestDto)
    {
        Booking booking = bookingMapper.toEntity(bookingRequestDto);
        if(roomService.getRoomAvailability(booking.getRoom().getId(),
                                            booking.getStartTime(),
                                            booking.getEndTime()))
        {
            throw new RuntimeException("Room is not available");
        }

        bookingRepository.save(booking);

        return bookingMapper.toDTO(booking);
    }

    public List<BookingResponseDto> getAllBookings()
    {
        List<Booking> bookings = bookingRepository.findAll();
        if(bookings.isEmpty())
            throw new RuntimeException("no bookings found");
        return bookingMapper.toDTOList(bookings);
    }

    public BookingResponseDto getBookingById(Long id)
    {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("booking not found"));
        return bookingMapper.toDTO(booking);
    }

    public void deleteBookingById(Long id)
    {
        if(!bookingRepository.existsById(id))
            throw new RuntimeException("booking not found");
        bookingRepository.deleteById(id);
    }

    public void approveBooking(Long id)
    {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("booking not found"));
        booking.setStatus(APPROVED);
        bookingRepository.save(booking);
    }

    public void rejectBooking(Long id, BookingApprovalDto approvalDto)
    {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("booking not found"));

        BookingStatus status = BookingStatus.valueOf(approvalDto.getStatus());
        booking.setStatus(status);
        bookingRepository.save(booking);
    }

    public List<BookingHistoryDto> getBookingHistory(Long id)
    {
        List<BookingHistory> bookingHistories = bookingHistoryRepository.findByBooking_id(id);
        if(bookingHistories.isEmpty())
            throw new RuntimeException("no booking history found");
        return bookingHistoryMapper.toDTOList(bookingHistories);
    }
}
