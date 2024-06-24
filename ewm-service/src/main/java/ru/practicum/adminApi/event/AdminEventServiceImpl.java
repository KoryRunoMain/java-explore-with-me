package ru.practicum.adminApi.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminEventServiceImpl implements AdminEventService {
    private final AdminEventRepository repository;

    @Override
    public List<EventFullDto> getAllEventsByAdmin(List<Long> users, String state, List<Long> categories, String rangeStart, String rangeEnd, int from, int size) {
        return null;
    }

    @Override
    public EventFullDto updateEventByAdmin(Long eventId, UpdateEventAdminRequest eventAdminRequest) {
        return null;
    }
}
