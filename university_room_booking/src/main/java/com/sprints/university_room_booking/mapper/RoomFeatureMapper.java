package com.sprints.university_room_booking.mapper;

import com.sprints.university_room_booking.dto.RoomFeatureDto;
import com.sprints.university_room_booking.model.RoomFeature;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomFeatureMapper {

    public RoomFeatureDto toDTO(RoomFeature feature) {
        if (feature == null) return null;

        RoomFeatureDto dto = new RoomFeatureDto();
        dto.setId(feature.getId());
        dto.setName(feature.getName());
        return dto;
    }

    public RoomFeature toEntity(RoomFeatureDto dto) {
        if (dto == null) return null;

        RoomFeature f = new RoomFeature();
        f.setName(dto.getName());
        return f;
    }

    public RoomFeature updateEntityFromDto(RoomFeature existing, RoomFeatureDto dto) {
        if (existing == null || dto == null) return existing;
        if (dto.getName() != null) existing.setName(dto.getName());
        return existing;
    }

    public List<RoomFeatureDto> toDTOList(List<RoomFeature> list) {
        if (list == null) return null;
        return list.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<RoomFeature> toEntityList(List<RoomFeatureDto> list) {
        if (list == null) return null;
        return list.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
