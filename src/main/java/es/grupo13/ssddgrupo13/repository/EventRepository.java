package es.grupo13.ssddgrupo13.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.grupo13.ssddgrupo13.entities.Event;
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByType(String type);
    List<Event> findByTitle(String title);
    Optional<Event> findById(long id);
}
