package ru.practicum.api.privateApi.participation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.api.requestDto.EventRequestStatusUpdateRequest;
import ru.practicum.api.requestDto.EventRequestStatusUpdateResult;
import ru.practicum.api.requestDto.ParticipationRequestDto;

import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class PrivateParticipationController {
    private final PrivateParticipationService service;

    @GetMapping(path = "/{userId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getParticipationRequests(@Validated @Positive @PathVariable Long userId) {
        log.info("Get-request: getParticipationRequests, userId={}", userId);
        return service.getRequestsByUser(userId);
    }

    @PostMapping("/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createParticipationRequest(@Validated @Positive @PathVariable Long userId,
                                                              @Validated @Positive @PathVariable Long eventId) {
        log.info("Post-request: createParticipationRequest, userId={}, eventId={}", userId, eventId);
        return service.createRequestByUser(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public ParticipationRequestDto cancelParticipationRequest(@Validated @Positive @PathVariable Long userId,
                                                              @Validated @Positive @PathVariable Long requestId) {
        log.info("Patch-request: updateParticipationRequest, userId={}, requestId={}", userId, requestId);
        return service.cancelRequestByUser(userId, requestId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getParticRequestsForUserEvent(@Validated @Positive @PathVariable Long userId,
                                                                       @Validated @Positive @PathVariable Long eventId) {
        log.info("Get-request: getParticipationRequestsForUserEvent, userId={}, eventId={}", userId, eventId);
        return service.getRequestsForUserEventByUser(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public EventRequestStatusUpdateResult updateEventRequestStatus(@Validated @Positive @PathVariable Long userId,
                                                                   @Validated @Positive @PathVariable Long eventId,
                                                                   @Validated @RequestBody EventRequestStatusUpdateRequest updateRequestStatus) {
        log.info("Patch-request: updateEventRequestStatus, userId={}, eventId={}, updateRequestStatus={}",
                userId, eventId, updateRequestStatus);
        return service.updateEventRequestStatusByUser(userId, eventId, updateRequestStatus);
    }

}
