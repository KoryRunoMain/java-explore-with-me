package ru.practicum.api.privateApi.participation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.api.requestDto.EventRequestStatusUpdateRequest;
import ru.practicum.api.requestDto.EventRequestStatusUpdateResult;
import ru.practicum.api.requestDto.ParticipationRequestDto;
import ru.practicum.common.enums.EventState;
import ru.practicum.common.enums.ParticipationStatus;
import ru.practicum.common.exception.ForbiddenException;
import ru.practicum.common.exception.NotFoundException;
import ru.practicum.common.exception.ValidationException;
import ru.practicum.common.mapper.ParticipationMapper;
import ru.practicum.persistence.model.Event;
import ru.practicum.persistence.model.ParticipationRequest;
import ru.practicum.persistence.repository.EventRepository;
import ru.practicum.persistence.repository.ParticipationRepository;
import ru.practicum.persistence.repository.UserRepository;
import ru.practicum.persistence.model.User;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PrivateParticipationServiceImpl implements PrivateParticipationService {
    private final ParticipationRepository repository;
    private final ParticipationMapper participationMapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;


    @Override
    public List<ParticipationRequestDto> getRequestsByUser(Long userId) {
        validateUserId(userId);
        return repository.findByRequesterId(userId)
                .stream()
                .flatMap(Optional::stream)
                .map(participationMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto createRequestByUser(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("PRIVATE-MESSAGE-response: userId NotFound"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("PRIVATE-MESSAGE-response: eventId NotFound"));

        validateParticipationRequest(userId, eventId, user, event);
        ParticipationStatus status = (event.getRequestModeration() && event.getParticipantLimit() != 0)
                ? ParticipationStatus.PENDING
                : ParticipationStatus.CONFIRMED;

        ParticipationRequest newRequest = ParticipationRequest.builder()
                .created(LocalDateTime.now())
                .event(event)
                .requester(user)
                .status(status)
                .build();
        return participationMapper.toParticipationRequestDto(repository.save(newRequest));
    }

    @Override
    public ParticipationRequestDto cancelRequestByUser(Long userId, Long requestId) {
        validateUserId(userId);
        ParticipationRequest requestToCancel = repository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("PRIVATE-MESSAGE-response: requestId NotFound"));
        requestToCancel.setStatus(ParticipationStatus.CANCELED);
        return participationMapper.toParticipationRequestDto(repository.save(requestToCancel));
    }

    @Override
    public List<ParticipationRequestDto> getRequestsForUserEventByUser(Long userId, Long eventId) {
        validateUserId(userId);
        return repository.findByEventIn(eventRepository.findByIdAndInitiatorId(eventId, userId))
                .stream()
                .flatMap(Optional::stream)
                .map(participationMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventRequestStatusUpdateResult updateEventRequestStatusByUser(Long userId, Long eventId,
                                                                         EventRequestStatusUpdateRequest eventRequestStatusUpdate) {
        validateUserId(userId);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("PRIVATE-MESSAGE-response: eventId NotFound"));

        List<ParticipationRequest> requests = repository.findAllById(eventRequestStatusUpdate.getRequestIds());
        EventRequestStatusUpdateResult resultDto = new EventRequestStatusUpdateResult(new ArrayList<>(), new ArrayList<>());

        ParticipationStatus newStatus;
        try {
            newStatus = ParticipationStatus.valueOf(String.valueOf(eventRequestStatusUpdate.getStatus()));
        } catch (IllegalArgumentException e) {
            throw new ValidationException("PRIVATE-MESSAGE-response: Invalid status value: " + eventRequestStatusUpdate.getStatus());
        }

        if (newStatus == ParticipationStatus.CONFIRMED) {
            doConfirmRequests(event, requests, resultDto);
        } else if (newStatus == ParticipationStatus.REJECTED) {
            doRejectRequests(requests, resultDto);
        }
        return resultDto;
    }

    private void validateUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("PRIVATE-MESSAGE-response: userId NotFound"));
    }

    private void validateParticipationRequest(Long userId, Long eventId, User user, Event event) {
        if (repository.existsByRequesterIdAndEventId(userId, eventId)) {
            throw new ForbiddenException("PRIVATE-MESSAGE-response: request fail, has already been added");
        }
        if (event.getInitiator().getId().equals(userId)) {
            throw new ForbiddenException("PRIVATE-MESSAGE-response: own event request fail, initiator could not add request for own event ");
        }
        if (event.getState() != EventState.PUBLISHED) {
            throw new ForbiddenException("PRIVATE-MESSAGE-response: request event fail, event is not published yet");
        }
        if (event.getParticipantLimit() != 0
                && repository.countByEventIdAndStatus(eventId, ParticipationStatus.CONFIRMED)
                >= event.getParticipantLimit()) {
            throw new ForbiddenException("PRIVATE-MESSAGE-response: participant limit");
        }
    }

    private void doConfirmRequests(Event event, List<ParticipationRequest> requests,
                                   EventRequestStatusUpdateResult resultDto) {
        int limitParticipants = event.getParticipantLimit();
        int countParticipants = repository.countByEventIdAndStatus(event.getId(), ParticipationStatus.CONFIRMED);

        if (limitParticipants == 0 || !event.getRequestModeration()) {
            throw new ForbiddenException("PRIVATE-MESSAGE-response: do not need to accept request, limit=0 : pre-moderation off");
        }
        if (countParticipants > limitParticipants) {
            throw new ForbiddenException("PRIVATE-MESSAGE-response: Participant limit is full");
        }

        for (ParticipationRequest request : requests) {
            if (request.getStatus() != ParticipationStatus.PENDING) {
                throw new ForbiddenException("PRIVATE-MESSAGE-response: status not PENDING");
            }
        }

        requests.forEach(request -> {
                    if (countParticipants < limitParticipants) {
                        request.setStatus(ParticipationStatus.CONFIRMED);
                        resultDto.getConfirmedRequests().add(participationMapper.toParticipationRequestDto(request));
                    }
                    else {
                        request.setStatus(ParticipationStatus.REJECTED);
                        resultDto.getRejectedRequests().add(participationMapper.toParticipationRequestDto(request));
                    }
                });
        repository.saveAll(requests);

        if (countParticipants == limitParticipants) {
            repository.updateStatusByEventAndCurrentStatus(event, ParticipationStatus.PENDING, ParticipationStatus.REJECTED);
        }
    }

    private void doRejectRequests(List<ParticipationRequest> requests, EventRequestStatusUpdateResult resultDto) {
        requests.stream()
                .filter(request -> {
                    if(request.getStatus() != ParticipationStatus.PENDING) {
                        throw new ForbiddenException("PRIVATE-MESSAGE-response: status of request not PENDING");
                    }
                    return true;
                })

                .forEach(request -> {
                    request.setStatus(ParticipationStatus.REJECTED);
                    resultDto.getRejectedRequests().add(participationMapper.toParticipationRequestDto(request));
                });
        repository.saveAll(requests);
    }

}
