package ru.practicum.api.adminApi.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.api.responseDto.EventFullDto;
import ru.practicum.api.requestDto.UpdateEventAdminRequest;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping
public class AdminEventController {
    private final AdminEventService service;

    @GetMapping(path = "/admin/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> getAllEvents(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<String> state,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Get-request: getAllEvents, users={}, state={}. categories={}, rangeStart={}, rangeEnd={}, from={}, size={}",
                users, state, categories, rangeStart, rangeEnd, from, size);
        return service.getAllEventsByAdmin(users, state, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping(path = "/admin/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEvent(@Validated @PathVariable Long eventId,
                                    @Validated @RequestBody UpdateEventAdminRequest eventAdminRequest) {
        log.info("Patch-request: updateEvent, eventId={}, eventAdminRequest={}", eventId, eventAdminRequest);
        return service.updateEventByAdmin(eventId, eventAdminRequest);
    }

}
