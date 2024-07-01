package ru.practicum.api.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class ParticipationRequestDto {

    private String created;
    private Long event;
    private Long id;
    private Long requester;
    private String status;

}
