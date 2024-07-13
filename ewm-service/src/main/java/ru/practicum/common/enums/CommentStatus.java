package ru.practicum.common.enums;

import java.util.Optional;

public enum CommentStatus {
    PENDING,
    APPROVED,
    REJECTED,
    SPAM,
    BLOCKED;

    public static Optional<CommentStatus> from(String stringStatus) {
        for (CommentStatus status : values()) {
            if (status.name().equalsIgnoreCase(stringStatus)) {
                return Optional.of(status);
            }
        }
        return Optional.empty();
    }

}
