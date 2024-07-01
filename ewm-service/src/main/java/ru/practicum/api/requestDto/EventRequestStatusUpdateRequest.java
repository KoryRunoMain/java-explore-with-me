package ru.practicum.api.requestDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.practicum.common.enums.RequestStatus;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class EventRequestStatusUpdateRequest {

    private List<Long> requestIds;
    private RequestStatus status;

}
