package ru.practicum.persistence.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.common.enums.EventState;
import ru.practicum.persistence.model.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "SELECT * FROM Event e " +
            "WHERE e.state = 'PUBLISHED' " +
            "AND (:text IS NULL OR lower(e.annotation) LIKE lower(concat('%', :text, '%')) " +
            "OR lower(e.description) LIKE lower(concat('%', :text, '%'))) " +
            "AND (:categories IS NULL OR e.category_id IN (:categories)) " +
            "AND (:paid IS NULL OR e.paid = :paid) " +
            "AND (e.event_date >= :rangeStart) " +
            "AND (:rangeEnd IS NULL OR e.event_date < :rangeEnd)",
            nativeQuery = true)
    List<Event> findAllPublicEvents(@Param("text") String text,
                                    @Param("categories") List<Long> categories,
                                    @Param("paid") Boolean paid,
                                    @Param("rangeStart") LocalDateTime rangeStart,
                                    @Param("rangeEnd") LocalDateTime rangeEnd,
                                    Pageable pageable);

    Optional<Event> findByIdAndState(Long eventId, EventState state);

    List<Event> findAllByInitiatorId(Long userId, PageRequest page);

    @Query(value = "SELECT * FROM Event e " +
            "WHERE (:userId IS NULL OR e.initiator_id IN (:userId)) " +
            "AND (:states IS NULL OR e.state IN (:states)) " +
            "AND (:categories IS NULL OR e.category_id IN (:categories)) " +
            "AND (:rangeStart IS NULL OR e.event_date >= :rangeStart) " +
            "AND (:rangeEnd IS NULL OR e.event_date < :rangeEnd)",
            nativeQuery = true)
    List<Event> findAllEventsByAdmin(@Param("userId") List<Long> userId,
                                     @Param("states") List<String> states,
                                     @Param("categories") List<Long> categories,
                                     @Param("rangeStart") LocalDateTime rangeStart,
                                     @Param("rangeEnd") LocalDateTime rangeEnd,
                                     Pageable pageable);

    Optional<Event> findFirstByCategoryId(Long catId);

    List<Event> findByIdIn(List<Long> eventIds);

    List<Event> findByIdAndInitiatorId(Long eventId, Long userId);
}
