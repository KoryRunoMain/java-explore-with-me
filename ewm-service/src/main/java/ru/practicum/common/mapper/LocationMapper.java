package ru.practicum.common.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.api.responseDto.LocationDto;
import ru.practicum.persistence.model.Location;

@Component
public class LocationMapper {

    public LocationDto toLocationDto(Location location) {
        return LocationDto.builder()
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }

    public Location toLocation(LocationDto locationDto) {
        return Location.builder()
                .lat(locationDto.getLat())
                .lon(locationDto.getLon())
                .build();
    }

}
