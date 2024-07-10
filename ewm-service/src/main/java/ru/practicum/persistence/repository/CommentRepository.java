package ru.practicum.persistence.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.persistence.model.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByEventId(Long eventId, PageRequest page);

    @Query("SELECT c FROM Comment c " +
            "WHERE (:text IS NULL OR c.text LIKE %:text%) " +
            "AND (:events IS NULL OR c.event.id IN :events) " +
            "AND (:rangeStart IS NULL OR c.created >= :rangeStart) " +
            "AND (:rangeEnd IS NULL OR c.created <= :rangeEnd) " +
            "AND (:status IS NULL OR c.status = :status)")
    List<Comment> searchComments(@Param("text") String text,
                                 @Param("events") List<Long> events,
                                 @Param("rangeStart") LocalDateTime rangeStart,
                                 @Param("rangeEnd") LocalDateTime rangeEnd,
                                 @Param("status") String status,
                                 PageRequest page);
}
