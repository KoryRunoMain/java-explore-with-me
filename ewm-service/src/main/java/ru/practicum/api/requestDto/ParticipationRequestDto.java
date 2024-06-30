package ru.practicum.api.requestDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ParticipationRequestDto {

    private String created;
    private Long event;
    private Long id;
    private Long requester;
    private String status;

}
