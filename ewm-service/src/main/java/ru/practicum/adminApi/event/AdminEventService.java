package ru.practicum.adminApi.event;

import java.util.List;

public interface AdminEventService {

    List<EventFullDto> getAllEventsByAdmin(List<Long> users, String state, List<Long> categories,
                                           String rangeStart, String rangeEnd, int from, int size);

    EventFullDto updateEventByAdmin(Long eventId, UpdateEventAdminRequest eventAdminRequest);

}
