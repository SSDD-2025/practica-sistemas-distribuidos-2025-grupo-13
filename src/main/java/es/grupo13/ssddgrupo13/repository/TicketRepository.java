package es.grupo13.ssddgrupo13.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.grupo13.ssddgrupo13.model.Ticket;
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByTitle(String title);
    Optional<Ticket> findById(long id);
}
