package ru.practicum.api.requestDto;

import ru.practicum.common.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class EventRequestStatusUpdateRequest {

    private List<Long> requestIds;
    private RequestStatus status;

}
