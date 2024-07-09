package ru.practicum.api.privateApi.participation;

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
import ru.practicum.api.requestDto.EventRequestStatusUpdateRequest;
import ru.practicum.api.responseDto.EventRequestStatusUpdateResult;
import ru.practicum.api.responseDto.ParticipationRequestDto;

import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping
public class PrivateParticipationController {
    private final PrivateParticipationService service;

    @GetMapping(path = "/users/{userId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getParticipationRequests(@Validated @Positive @PathVariable Long userId) {
        log.info("Get-request: getParticipationRequests, userId={}", userId);
        return service.getRequestsByUser(userId);
    }

    @PostMapping(path = "/users/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createParticipationRequest(@Validated @Positive @PathVariable Long userId,
                                                              @Validated @Positive @RequestParam Long eventId) {
        log.info("Post-request: createParticipationRequest, userId={}, eventId={}", userId, eventId);
        return service.createRequestByUser(userId, eventId);
    }

    @PatchMapping(path = "/users/{userId}/requests/{requestId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public ParticipationRequestDto cancelParticipationRequest(@Validated @Positive @PathVariable Long userId,
                                                              @Validated @Positive @PathVariable Long requestId) {
        log.info("Patch-request: updateParticipationRequest, userId={}, requestId={}", userId, requestId);
        return service.cancelRequestByUser(userId, requestId);
    }

    @GetMapping(path = "/users/{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getParticipationRequestsForUserEvent(@Validated @Positive @PathVariable Long userId,
                                                                       @Validated @Positive @PathVariable Long eventId) {
        log.info("Get-request: getParticipationRequestsForUserEvent, userId={}, eventId={}", userId, eventId);
        return service.getRequestsForUserEventByUser(userId, eventId);
    }

    @PatchMapping(path = "/users/{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public EventRequestStatusUpdateResult updateEventRequestStatus(@Validated @Positive @PathVariable Long userId,
                                                                   @Validated @Positive @PathVariable Long eventId,
                                                                   @Validated @RequestBody EventRequestStatusUpdateRequest updateRequestStatus) {
        log.info("Patch-request: updateEventRequestStatus, userId={}, eventId={}, updateRequestStatus={}",
                userId, eventId, updateRequestStatus);
        return service.updateEventRequestStatus(userId, eventId, updateRequestStatus);
    }

}
