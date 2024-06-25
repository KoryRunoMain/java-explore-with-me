package ru.practicum.api.privateApi.event;

import ru.practicum.api.responseDto.EventFullDto;
import ru.practicum.api.responseDto.EventShortDto;
import ru.practicum.api.requestDto.NewEventDto;

import java.util.List;

public interface PrivateEventService {

    List<EventShortDto> getUserEventsByUser(Long userId, int from, int size);

    EventFullDto createEventByUser(Long userId, NewEventDto eventDto);

    EventFullDto getEventByUser(Long userId, Long eventId);

    EventFullDto updateEventByUser(Long userId, Long eventId, NewEventDto eventDto);

}
