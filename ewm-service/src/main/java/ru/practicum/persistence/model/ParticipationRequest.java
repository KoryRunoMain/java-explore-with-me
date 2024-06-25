package ru.practicum.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.common.enums.ParticipationStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "participation")
public class ParticipationRequest {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created")
    private LocalDateTime created;

    @ManyToOne
    @Column(name = "event_id")
    private Event event;

    @ManyToOne
    @Column(name = "requester_id")
    private User requester;

    @Column(name = "status", length = 11)
    private ParticipationStatus status;

}
