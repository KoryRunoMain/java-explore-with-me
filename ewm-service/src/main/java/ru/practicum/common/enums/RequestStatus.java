package ru.practicum.common.enums;

import java.util.Optional;

public enum RequestStatus {
    CONFIRMED,
    REJECTED;

    public static Optional<RequestStatus> from(String stringRequestStatus) {
        for (RequestStatus status : values()) {
            if (status.name().equalsIgnoreCase(stringRequestStatus)) {
                return Optional.of(status);
            }
        }
        return Optional.empty();
    }

}
