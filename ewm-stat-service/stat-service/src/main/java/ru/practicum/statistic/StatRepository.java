package ru.practicum.statistic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.statistic.model.EndpointHit;
import ru.practicum.statistic.model.ViewStat;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepository extends JpaRepository<EndpointHit, Long> {

    @Query("SELECT new ru.practicum.statistic.model.ViewStat(eh.app, eh.uri, COUNT(DISTINCT eh.ip)) " +
            "FROM EndpointHit eh " +
            "WHERE eh.timestamp BETWEEN :start AND :end " +
            "AND (:uris IS NULL OR eh.uri IN :uris) " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY COUNT(DISTINCT eh.ip) DESC")
    Page<ViewStat> findAllUniqueViewStatList(LocalDateTime start, LocalDateTime end, List<String> uris, Pageable page);

    @Query("SELECT new ru.practicum.statistic.model.ViewStat(eh.app, eh.uri, COUNT(eh.ip)) " +
            "FROM EndpointHit eh " +
            "WHERE eh.timestamp BETWEEN :start AND :end " +
            "AND (:uris IS NULL OR eh.uri IN :uris) " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY COUNT(eh.ip) DESC")
    Page<ViewStat> findAllViewStatList(LocalDateTime start, LocalDateTime end, List<String> uris, Pageable page);

}
