package es.grupo13.ssddgrupo13.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.grupo13.ssddgrupo13.model.Event;
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByType(String type);
    List<Event> findByTitle(String title);
    Optional<Event> findById(long id);
    default void deleteById(long id) {
        Optional<Event> eventOpt = findById(id);
        if (eventOpt.isPresent()) {
            Event event = eventOpt.get();
            if (event.getComments() != null) {
                event.getComments().forEach(c -> c.setEvent(null));
                event.getComments().clear();
            }
            delete(event);
        }
    }
}
