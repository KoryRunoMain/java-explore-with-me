package ru.practicum.common.enums;

import java.util.Optional;

public enum PrivateStateAction {
    SEND_TO_REVIEW,
    CANCEL_REVIEW;

    public static Optional<PrivateStateAction> from(String stringStateAction) {
        for (PrivateStateAction state : values()) {
            if (state.name().equalsIgnoreCase(stringStateAction)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }

}
