package com.sprints.university_room_booking.mapper;

import com.sprints.university_room_booking.dto.RoomDto;
import com.sprints.university_room_booking.dto.RoomSummaryDto;
import com.sprints.university_room_booking.model.Building;
import com.sprints.university_room_booking.model.Department;
import com.sprints.university_room_booking.model.Room;
import com.sprints.university_room_booking.model.RoomFeature;
import com.sprints.university_room_booking.repository.BuildingRepository;
import com.sprints.university_room_booking.repository.DepartmentRepository;
import com.sprints.university_room_booking.repository.RoomFeatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoomMapper {

    // Inject repositories to fetch related entities
    private final BuildingRepository buildingRepository;
    private final DepartmentRepository departmentRepository;
    private final RoomFeatureRepository roomFeatureRepository;

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
        room.setId(dto.getId()); // Set ID if provided
        room.setRoomNumber(dto.getRoomNumber());
        room.setCapacity(dto.getCapacity());

        // FIXED: Set Building relationship from buildingId
        if (dto.getBuildingId() != null) {
            Building building = buildingRepository.findById(dto.getBuildingId())
                    .orElseThrow(() -> new RuntimeException("Building not found with ID: " + dto.getBuildingId()));
            room.setBuilding(building);
        }

        // FIXED: Set Department relationship from departmentId
        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found with ID: " + dto.getDepartmentId()));
            room.setDepartment(department);
        }

        // FIXED: Set RoomFeatures from featureIds
        if (dto.getFeatureIds() != null && !dto.getFeatureIds().isEmpty()) {
            Set<RoomFeature> features = new HashSet<>();
            for (Long featureId : dto.getFeatureIds()) {
                RoomFeature feature = roomFeatureRepository.findById(featureId)
                        .orElseThrow(() -> new RuntimeException("Room feature not found with ID: " + featureId));
                features.add(feature);
            }
            room.setFeatures(features);
        } else {
            room.setFeatures(new HashSet<>());
        }

        return room;
    }

    public Room updateEntityFromDto(Room existing, RoomDto dto) {
        if (existing == null || dto == null) return existing;

        if (dto.getRoomNumber() != null) {
            existing.setRoomNumber(dto.getRoomNumber());
        }
        if (dto.getCapacity() != null) {
            existing.setCapacity(dto.getCapacity());
        }

        // Update Building relationship
        if (dto.getBuildingId() != null) {
            Building building = buildingRepository.findById(dto.getBuildingId())
                    .orElseThrow(() -> new RuntimeException("Building not found with ID: " + dto.getBuildingId()));
            existing.setBuilding(building);
        }

        // Update Department relationship
        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found with ID: " + dto.getDepartmentId()));
            existing.setDepartment(department);
        }

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