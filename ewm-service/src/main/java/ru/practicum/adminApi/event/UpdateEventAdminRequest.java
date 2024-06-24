package ru.practicum.adminApi.event;

import ru.practicum.adminApi.location.Location;

import javax.validation.constraints.Size;
import java.util.Optional;

public class UpdateEventAdminRequest {

    @Size(min = 20, max = 2000)
    private String annotation;

    private Long category;

    @Size(min = 20, max = 7000)
    private String description;

    private String eventDate;
    private Location location;
    private boolean paid;
    private Integer participantLimit;
    private boolean requestModeration;
    private StateAction stateAction;

    @Size(min = 3, max = 120)
    private String title;


    protected enum StateAction {
        PUBLISH_EVENT,
        REJECT_EVENT;

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
