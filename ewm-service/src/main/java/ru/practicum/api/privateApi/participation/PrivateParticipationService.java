package ru.practicum.api.privateApi.participation;

import ru.practicum.api.requestDto.EventRequestStatusUpdateRequest;
import ru.practicum.api.requestDto.EventRequestStatusUpdateResult;
import ru.practicum.api.requestDto.ParticipationRequestDto;
import java.util.List;

public interface PrivateParticipationService {

    List<ParticipationRequestDto> getRequestsByUser(Long userId);

    ParticipationRequestDto createRequestByUser(Long userId, Long eventId);

    ParticipationRequestDto cancelRequestByUser(Long userId, Long eventId);

    List<ParticipationRequestDto> getRequestsForUserEventByUser(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateEventRequestStatusByUser(Long userId, Long eventId,
                                                                  EventRequestStatusUpdateRequest updateRequestStatus);

}
