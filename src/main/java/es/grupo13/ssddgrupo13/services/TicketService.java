package es.grupo13.ssddgrupo13.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.grupo13.ssddgrupo13.model.Ticket;
import es.grupo13.ssddgrupo13.repository.TicketRepository;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    List<Ticket> findByTitle(String title) {
        return ticketRepository.findByTitle(title);
    }

    public Optional<Ticket> findById(long id) {
        return ticketRepository.findById(id);
    }
    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

}
