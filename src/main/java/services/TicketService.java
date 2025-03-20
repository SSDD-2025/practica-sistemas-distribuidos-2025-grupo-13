package services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.grupo13.ssddgrupo13.entities.Event;
import es.grupo13.ssddgrupo13.repository.TicketRepository;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    List<Event> findByTitle(String title) {
        return ticketRepository.findByTitle(title);
    }

    public Optional<Event> findById(long id) {
        return ticketRepository.findById(id);
    }

}
