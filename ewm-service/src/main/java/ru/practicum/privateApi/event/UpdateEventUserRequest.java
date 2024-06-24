package ru.practicum.privateApi.event;

import ru.practicum.adminApi.location.Location;

import javax.validation.constraints.Size;
import java.util.Optional;

public class UpdateEventUserRequest {

    private String eventDate;
    private Long category;
    private Location location;
    private StateAction stateAction;
    private Integer participantLimit;
    private boolean paid;
    private boolean requestModeration;

    @Size(min = 20, max = 7000)
    private String description;
    @Size(min = 20, max = 2000)
    private String annotation;
    @Size(min = 3, max = 120)
    private String title;

    protected enum StateAction {
        SEND_TO_REVIEW,
        CANCEL_REVIEW;

        public static Optional<StateAction> from(String stringStateAction) {
            for (StateAction state : values()) {
                if (state.name().equalsIgnoreCase(stringStateAction)) {
                    return Optional.of(state);
                }
            }
            return Optional.empty();
        }

    }

}
