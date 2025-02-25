package es.grupo13.ssddgrupo13.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.grupo13.ssddgrupo13.entities.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
}
