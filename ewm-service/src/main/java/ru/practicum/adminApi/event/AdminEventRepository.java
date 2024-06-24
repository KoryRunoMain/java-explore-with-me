package ru.practicum.adminApi.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminEventRepository extends JpaRepository<Event, Long> {



}
