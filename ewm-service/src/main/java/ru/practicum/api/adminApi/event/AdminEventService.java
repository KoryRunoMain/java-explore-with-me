package ru.practicum.api.adminApi.event;

import ru.practicum.api.responseDto.EventFullDto;
import ru.practicum.api.requestDto.UpdateEventAdminRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventService {

    List<EventFullDto> getAllEventsByAdmin(List<Long> users, List<String> state, List<Long> categories,
                                           LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size);

    EventFullDto updateEventByAdmin(Long eventId, UpdateEventAdminRequest eventAdminRequest);

}
