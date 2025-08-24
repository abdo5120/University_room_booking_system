package com.sprints.university_room_booking.mapper;

import com.sprints.university_room_booking.dto.BookingRequestDto;
import com.sprints.university_room_booking.dto.BookingResponseDto;
import com.sprints.university_room_booking.model.Booking;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingMapper {

    private final RoomMapper roomMapper;
    private final UserMapper userMapper;

    public BookingMapper(RoomMapper roomMapper, UserMapper userMapper) {
        this.roomMapper = roomMapper;
        this.userMapper = userMapper;
    }

    public Booking toEntity(BookingRequestDto dto) {
        if (dto == null) return null;

        Booking b = new Booking();
        b.setStartTime(dto.getStartTime());
        b.setEndTime(dto.getEndTime());
        b.setPurpose(dto.getPurpose());
        return b;
    }

    public BookingResponseDto toDTO(Booking booking) {
        if (booking == null) return null;

        BookingResponseDto dto = new BookingResponseDto();
        dto.setId(booking.getId());
        dto.setStartTime(booking.getStartTime());
        dto.setEndTime(booking.getEndTime());
        dto.setPurpose(booking.getPurpose());
        dto.setStatus(booking.getStatus() != null ? booking.getStatus().name() : null);

        dto.setRoom(roomMapper.toSummary(booking.getRoom()));
        dto.setUser(userMapper.toSummary(booking.getUser()));
        return dto;
    }

    public List<BookingResponseDto> toDTOList(List<Booking> bookings) {
        if (bookings == null) return null;
        return bookings.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
