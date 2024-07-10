package ru.practicum.common.enums;

import java.util.Optional;

public enum CommentStatus {
    PENDING,
    APPROVED,
    REJECTED,
    SPAM,

    /*
        Status 'DELETED' is intended for cases when soft deletion of data from the database is required.
        This is not provided in this case but if its required, add a new flag soft deletion
        to the 'Сomment' entity and revision is required.
        private Boolean isDeleted; // flag soft deletion
     */
    DELETED;

    public static Optional<CommentStatus> from(String stringStatus) {
        for (CommentStatus status : values()) {
            if (status.name().equalsIgnoreCase(stringStatus)) {
                return Optional.of(status);
            }
        }
        return Optional.empty();
    }

}
