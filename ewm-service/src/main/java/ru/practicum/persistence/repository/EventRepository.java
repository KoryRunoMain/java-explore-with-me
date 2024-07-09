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

    Optional<Event> findByIdAndState(Long eventId, EventState state);

    boolean existsByCategoryId(Long catId);

    List<Event> findByIdIn(List<Long> eventIds);

    List<Event> findAllByInitiatorId(Long userId, PageRequest page);

    List<Event> findByIdAndInitiatorId(Long eventId, Long userId);

    @Query(value = "SELECT * " +
            "FROM event e " +
            "WHERE (:userId IS NULL OR e.initiator_id IN (CAST(CAST(:userId AS TEXT) AS BIGINT))) " +
            "AND (:states IS NULL OR e.state IN (CAST(:states AS TEXT))) " +
            "AND (:categories IS NULL OR e.category_id IN (CAST(CAST(:categories AS TEXT) AS BIGINT))) " +
            "AND (CAST(:rangeStart AS TIMESTAMP) IS NULL OR e.event_date >= CAST(:rangeStart AS TIMESTAMP))" +
            "AND (CAST(:rangeEnd AS TIMESTAMP) IS NULL OR e.event_date < CAST(:rangeEnd AS TIMESTAMP))",
            nativeQuery = true)
        List<Event> findAllEventsByAdmin(@Param("userId") List<Long> userId,
                                         @Param("states") List<String> states,
                                         @Param("categories") List<Long> categories,
                                         @Param("rangeStart") LocalDateTime rangeStart,
                                         @Param("rangeEnd") LocalDateTime rangeEnd,
                                         Pageable pageable);

    @Query(value = "SELECT * " +
            "FROM event e WHERE (e.state = 'PUBLISHED') " +
            "AND (:text IS NULL OR LOWER(e.annotation) LIKE LOWER(CONCAT('%',CAST(:text AS TEXT),'%')) " +
            "OR lower(e.description) LIKE LOWER(concat('%',CAST(:text AS TEXT),'%'))) " +
            "AND (:categories IS NULL OR e.category_id IN (CAST(CAST(:categories AS TEXT) AS BIGINT))) " +
            "AND (:paid IS NULL OR e.paid = CAST(CAST(:paid AS TEXT) AS BOOLEAN)) " +
            "AND (e.event_date >= :rangeStart) " +
            "AND (CAST(:rangeEnd AS TIMESTAMP) IS NULL OR e.event_date < CAST(:rangeEnd AS TIMESTAMP))",
            nativeQuery = true)
        List<Event> findAllPublicEvents(@Param("text") String text,
                                        @Param("categories") List<Long> categories,
                                        @Param("paid") Boolean paid,
                                        @Param("rangeStart") LocalDateTime rangeStart,
                                        @Param("rangeEnd") LocalDateTime rangeEnd,
                                        Pageable pageable);

}
