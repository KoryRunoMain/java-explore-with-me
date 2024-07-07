package ru.practicum.api.privateApi.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.api.requestDto.UpdateEventUserRequest;
import ru.practicum.api.responseDto.EventFullDto;
import ru.practicum.api.responseDto.EventShortDto;
import ru.practicum.api.requestDto.NewEventDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping
public class PrivateEventController {
    private final PrivateEventService service;

    @GetMapping(path = "/users/{userId}/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getAllEvents(@Validated @Positive @PathVariable Long userId,
                                            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                            @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Get-request: getUserEventByUser, userId={}, from={}, size={}", userId, from, size);
        return service.getUserEventsByUser(userId, from, size);
    }

    @PostMapping(path = "/users/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@Validated @Positive @PathVariable Long userId,
                                    @Validated @RequestBody NewEventDto newEvent) {
        log.info("Post-request: createEvent, userId={}, newEvent={}", userId, newEvent);
        return service.createEventByUser(userId, newEvent);
    }

    @GetMapping(path = "/users/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getEvent(@Validated @Positive @PathVariable Long userId,
                                 @Validated @Positive @PathVariable Long eventId) {
        log.info("Get-request: getEventById, userId={}, eventId={}", userId, eventId);
        return service.getEventByUser(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEvent(@Validated @Positive @PathVariable Long userId,
                                    @Validated @Positive @PathVariable Long eventId,
                                    @Validated @RequestBody UpdateEventUserRequest newEvent) {
        log.info("Patch-request: updateEvent, userId={}, eventId={}, newEvent={}", userId, eventId, newEvent);
        return service.updateEventByUser(userId, eventId, newEvent);
    }

}
