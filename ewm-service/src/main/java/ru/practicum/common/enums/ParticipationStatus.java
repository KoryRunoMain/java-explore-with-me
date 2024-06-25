package ru.practicum.common.enums;

import java.util.Optional;

public enum ParticipationStatus {
    PENDING,
    CONFIRMED,
    REJECTED,
    CANCELED;

    public static Optional<ParticipationStatus> from(String stringStatus) {
        for (ParticipationStatus status : values()) {
            if (status.name().equalsIgnoreCase(stringStatus)) {
                return Optional.of(status);
            }
        }
        return Optional.empty();
    }

}
