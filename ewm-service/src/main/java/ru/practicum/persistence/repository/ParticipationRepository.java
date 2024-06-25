package ru.practicum.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.common.enums.ParticipationStatus;
import ru.practicum.persistence.model.Event;
import ru.practicum.persistence.model.ParticipationRequest;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipationRepository extends JpaRepository<ParticipationRequest, Long> {

    Integer countByEventIdAndStatus(Long eventId, ParticipationStatus participationStatus);

    boolean existsByRequesterIdAndEventId(Long userId, Long eventId);

    List<Optional<ParticipationRequest>> findByEventIn(List<Event> events);

    List<Optional<ParticipationRequest>> findByRequesterId(Long userId);

    @Modifying
    @Query("UPDATE ParticipationRequest r " +
            "SET r.status = :newStatus " +
            "WHERE r.event = :event " +
            "AND r.status = :searchStatus")
    void updateStatusByEventAndCurrentStatus(@Param("event") Event event,
                                             @Param("searchStatus") ParticipationStatus searchStatus,
                                             @Param("newStatus") ParticipationStatus newStatus);

}
