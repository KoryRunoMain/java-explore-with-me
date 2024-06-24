package ru.practicum.privateApi.participation;

import java.util.List;

public class EventRequestStatusUpdateRequest {

    protected enum RequestStatus {
        CONFIRMED,
        REJECTED
    }

    private List<Long> requestIds;
    private RequestStatus status;

}
