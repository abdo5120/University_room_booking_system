package com.sprints.university_room_booking.mapper;

import com.sprints.university_room_booking.dto.RoomDto;
import com.sprints.university_room_booking.dto.RoomSummaryDto;
import com.sprints.university_room_booking.model.Room;
import com.sprints.university_room_booking.model.RoomFeature;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoomMapper {

    public RoomDto toDTO(Room room) {
        if (room == null) return null;

        RoomDto dto = new RoomDto();
        dto.setId(room.getId());
        dto.setRoomNumber(room.getRoomNumber());
        dto.setCapacity(room.getCapacity());

        if (room.getBuilding() != null) {
            dto.setBuildingId(room.getBuilding().getId());
        }

        if (room.getDepartment() != null) {
            dto.setDepartmentId(room.getDepartment().getId());
        }

        Set<Long> featureIds = room.getFeatures() == null
                ? Collections.emptySet()
                : room.getFeatures().stream().map(RoomFeature::getId).collect(Collectors.toSet());
        dto.setFeatureIds(featureIds);

        return dto;
    }

    public Room toEntity(RoomDto dto) {
        if (dto == null) return null;

        Room room = new Room();
        room.setRoomNumber(dto.getRoomNumber());
        room.setCapacity(dto.getCapacity());
        return room;
    }

    public Room updateEntityFromDto(Room existing, RoomDto dto) {
        if (existing == null || dto == null) return existing;
        if (dto.getRoomNumber() != null) existing.setRoomNumber(dto.getRoomNumber());
        if (dto.getCapacity() != null) existing.setCapacity(dto.getCapacity());
        return existing;
    }

    public RoomSummaryDto toSummary(Room room) {
        if (room == null) return null;

        RoomSummaryDto s = new RoomSummaryDto();
        s.setId(room.getId());
        s.setRoomNumber(room.getRoomNumber());
        s.setBuildingName(room.getBuilding() != null ? room.getBuilding().getName() : null);
        return s;
    }

    public List<RoomDto> toDTOList(List<Room> rooms) {
        if (rooms == null) return null;
        return rooms.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<RoomSummaryDto> toSummaryList(List<Room> rooms) {
        if (rooms == null) return null;
        return rooms.stream().map(this::toSummary).collect(Collectors.toList());
    }
}
