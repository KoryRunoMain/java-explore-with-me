package ru.practicum.api.publicApi.event;

import ru.practicum.api.responseDto.EventFullDto;
import ru.practicum.api.responseDto.EventShortDto;
import java.util.List;

public interface PublicEventService {

    List<EventShortDto> getAllPublicEvents(String text, List<Long> categories, Boolean paid, String rangeStart,
                                           String rangeEnd, boolean onlyAvailable, String sort, int from, int size);

    EventFullDto getPublicEvent(Long id);

}
