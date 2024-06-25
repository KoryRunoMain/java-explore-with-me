package ru.practicum.common.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.api.requestDto.ParticipationRequestDto;
import ru.practicum.common.enums.ParticipationStatus;
import ru.practicum.persistence.model.ParticipationRequest;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ParticipationMapper {

    public ParticipationRequestDto toParticipationRequestDto(ParticipationRequest request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .created(request.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .event(request.getEvent().getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus().toString())
                .build();
    }

    public long confirmedRequestsCounts(List<ParticipationRequest> requests) {
        if (requests == null) {
            return 0;
        }
        return requests.stream()
                .filter(request -> request.getStatus() == ParticipationStatus.CONFIRMED)
                .count();
    }

}
