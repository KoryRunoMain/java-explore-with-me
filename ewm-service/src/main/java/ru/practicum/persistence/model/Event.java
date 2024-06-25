package ru.practicum.persistence.model;

import lombok.*;
import ru.practicum.api.responseDto.CategoryDto;
import ru.practicum.common.enums.EventState;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "event")
public class Event {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "annotation", nullable = false)
    private String annotation;

    @ManyToOne
    @Column(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @Column(name = "initiator_id", nullable = false)
    private User initiator;

    @Column(name = "paid", nullable = false)
    private boolean paid;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "description")
    private String description;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @ManyToOne
    @Column(name = "location")
    private Location location;

    @Column(name = "participant_limit")
    private Integer participantLimit;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @Column(name = "request_moderation")
    private Boolean requestModeration;

    @Column(name = "confirmed_requests")
    private Long confirmedRequests;

    @Column(name = "state")
    private EventState state;

    @Column(name = "views")
    private Long views;

    @ManyToMany(mappedBy = "event")
    private List<Compilation> compilations;

    @OneToMany(mappedBy = "event")
    private List<ParticipationRequest> requests;

}
