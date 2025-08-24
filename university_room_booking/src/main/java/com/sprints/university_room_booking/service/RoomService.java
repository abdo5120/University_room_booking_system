package com.sprints.university_room_booking.service;

import com.sprints.university_room_booking.dto.RoomDto;
import com.sprints.university_room_booking.dto.RoomSummaryDto;
import com.sprints.university_room_booking.mapper.RoomMapper;
import com.sprints.university_room_booking.model.Booking;
import com.sprints.university_room_booking.model.Room;
import com.sprints.university_room_booking.model.RoomFeature;
import com.sprints.university_room_booking.repository.BookingRepository;
import com.sprints.university_room_booking.repository.RoomFeatureRepository;
import com.sprints.university_room_booking.repository.RoomRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoomService
{
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final RoomFeatureRepository roomFeatureRepository;
    private final RoomMapper roomMapper;

    public List<RoomSummaryDto> getAllRooms()
    {
        List<Room> rooms = roomRepository.findAll();
        if(rooms.isEmpty())
            throw new RuntimeException("No rooms found");
        return roomMapper.toSummaryList(rooms);
    }

    public RoomDto getRoomById(Long id)
    {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        return roomMapper.toDTO(room);
    }

    public boolean getRoomAvailability(Long roomId,LocalDateTime startDate, LocalDateTime endDate)
    {
        List<Booking> overlappingBookings =
                bookingRepository.findOverlappingBookings(roomId, startDate, endDate);

        return overlappingBookings.isEmpty();
    }

    @Transactional
    public RoomDto createRoom(@Valid RoomDto roomDto)
    {
        if (roomRepository.existsById(roomDto.getId()))
            throw new RuntimeException("Room already exists");
        Room room = roomMapper.toEntity(roomDto);
        roomRepository.save(room);
        return roomDto;
    }

    @Transactional
    public RoomDto updateRoom(Long roomId, @Valid RoomDto roomDto)
    {
        if (!roomRepository.existsById(roomId))
            throw new RuntimeException("Room not found");

        Room room = roomMapper.toEntity(roomDto);
        roomRepository.save(room);
        return roomDto;
    }

    @Transactional
    public void deleteRoom(Long roomId)
    {
        if (!roomRepository.existsById(roomId))
            throw new RuntimeException("Room not found");
        roomRepository.deleteById(roomId);
    }

    @Transactional
    public void attachFeatures(Long roomId, List<Long> roomFeatureId)
    {
        Set<RoomFeature> roomFeature = new HashSet<>();
        for (Long featureId : roomFeatureId)
            roomFeature.add(roomFeatureRepository.findById(featureId)
                    .orElseThrow(() -> new RuntimeException("Feature not found")));

        if (roomRepository.findById(roomId).isEmpty())
            throw new RuntimeException("Room not found");

        Room room = roomRepository.findById(roomId).get();
        room.getFeatures().clear();
        room.setFeatures(roomFeature);
        roomRepository.save(room);
    }

    @Transactional
    public void detachFeature(Long roomId, Long featureId)
    {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        RoomFeature roomFeature = roomFeatureRepository.findById(featureId)
                .orElseThrow(() -> new RuntimeException("Feature not found"));

        room.getFeatures().remove(roomFeature);
        roomRepository.save(room);
    }
}
