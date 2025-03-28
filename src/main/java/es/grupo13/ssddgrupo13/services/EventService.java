package es.grupo13.ssddgrupo13.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.grupo13.ssddgrupo13.model.Client;
import es.grupo13.ssddgrupo13.model.Comment;
import es.grupo13.ssddgrupo13.model.Event;
import es.grupo13.ssddgrupo13.model.Ticket;
import es.grupo13.ssddgrupo13.repository.ClientRepository;
import es.grupo13.ssddgrupo13.repository.CommentRepository;
import es.grupo13.ssddgrupo13.repository.EventRepository;
import es.grupo13.ssddgrupo13.repository.TicketRepository;

@Service
public class EventService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EventRepository eventRepository;

    public List<Event> findByType(String type){
        return eventRepository.findByType(type);
    }
    public List<Event> findByTitle(String title){
        return eventRepository.findByTitle(title);
    }
    public Optional<Event> findById(long id){
        return eventRepository.findById(id);
    }
    public void deleteById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));
    
        for (Comment comment : event.getComments()) {
            Client client = comment.getClient();
            if (client != null) {
                client.getComments().remove(comment); 
                clientRepository.save(client); 
            }
            commentRepository.delete(comment); 
        }

        for (Ticket ticket : event.getTickets()) {
            ticketRepository.delete(ticket);
        }
    
        eventRepository.delete(event);
    }
    public Event save(Event event){
        return eventRepository.save(event);
    }
    public Iterable<Event> findAll(){
        return eventRepository.findAll();
    }

}
