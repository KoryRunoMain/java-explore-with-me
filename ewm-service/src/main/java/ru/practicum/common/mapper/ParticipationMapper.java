package ru.practicum.common.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.api.responseDto.ParticipationRequestDto;
import ru.practicum.common.enums.ParticipationStatus;
import ru.practicum.persistence.model.ParticipationRequest;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ParticipationMapper {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final int EMPTY_REQUESTS_COUNT = 0;

    public ParticipationRequestDto toParticipationRequestDto(ParticipationRequest request) {
        return ParticipationRequestDto.builder()
                .created(request.getCreated().format(formatter))
                .event(request.getEvent().getId())
                .id(request.getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus().toString())
                .build();
    }

    public long confirmedRequestsCounts(List<ParticipationRequest> requests) {
        if (requests == null) {
            return EMPTY_REQUESTS_COUNT;
        }
        return requests.stream()
                .filter(request -> request.getStatus() == ParticipationStatus.CONFIRMED)
                .count();
    }

}
