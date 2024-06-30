package ru.practicum.api.publicApi.event;

import ru.practicum.api.responseDto.EventFullDto;
import ru.practicum.api.responseDto.EventShortDto;
import java.time.LocalDateTime;
import java.util.List;

public interface PublicEventService {

    List<EventShortDto> getAllPublicEvents(String text, List<Long> categories, boolean paid, LocalDateTime rangeStart,
                                           LocalDateTime rangeEnd, boolean onlyAvailable, String sort, int from, int size);

    EventFullDto getPublicEvent(Long id);

}
