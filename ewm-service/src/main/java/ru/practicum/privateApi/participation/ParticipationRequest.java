package ru.practicum.privateApi.participation;

import ru.practicum.adminApi.event.Event;
import ru.practicum.adminApi.account.User;

import javax.persistence.*;

@Entity
public class ParticipationRequest {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created")
    private String created;

    @Column(name = "event_id")
    private Event event;

    @ManyToOne
    @Column(name = "requester_id")
    private User requester;

    @Column(name = "status", length = 11)
    private ParticipationStatus status;

}
