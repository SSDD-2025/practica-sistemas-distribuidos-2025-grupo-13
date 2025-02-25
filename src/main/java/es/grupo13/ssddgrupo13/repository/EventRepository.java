package es.grupo13.ssddgrupo13.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.grupo13.ssddgrupo13.entities.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
    
}
