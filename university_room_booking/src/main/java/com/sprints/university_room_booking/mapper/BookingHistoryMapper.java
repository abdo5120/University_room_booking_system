package com.sprints.university_room_booking.mapper;

import com.sprints.university_room_booking.dto.BookingHistoryDto;
import com.sprints.university_room_booking.model.BookingHistory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingHistoryMapper {

    private final UserMapper userMapper;

    public BookingHistoryMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public BookingHistoryDto toDTO(BookingHistory history) {
        if (history == null) return null;

        BookingHistoryDto dto = new BookingHistoryDto();
        dto.setId(history.getId());
        dto.setStatus(history.getStatus() != null ? history.getStatus().name() : null);
        dto.setReason(history.getReason());
        dto.setTimestamp(history.getTimestamp());
        dto.setChangedByUser(userMapper.toSummary(history.getChangedByUser()));
        return dto;
    }

    public List<BookingHistoryDto> toDTOList(List<BookingHistory> list) {
        if (list == null) return null;
        return list.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
