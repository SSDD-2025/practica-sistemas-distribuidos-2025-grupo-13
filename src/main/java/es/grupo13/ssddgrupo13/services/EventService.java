package es.grupo13.ssddgrupo13.services;

import java.io.InputStream;
import java.net.URI;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import es.grupo13.ssddgrupo13.dto.EventDTO;
import es.grupo13.ssddgrupo13.dto.EventMapper;
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

    @Autowired
    private EventMapper eventMapper;

    public Collection<EventDTO> getConcerts() {
        return toDTOs(eventRepository.findByType("concierto"));
    }

    public Collection<EventDTO> getFestivals() {
        return toDTOs(eventRepository.findByType("festival"));
    }

    public Collection<EventDTO> getClubs() {
        return toDTOs(eventRepository.findByType("club"));
    }

    public Collection<EventDTO> getAllEvents() {
        return toDTOs(eventRepository.findAll());
    }

    public EventDTO getEvent(Long id) {
		return toDTO(eventRepository.findById(id).orElseThrow());
	}

    public List<Event> findByType(String type){
        return eventRepository.findByType(type);
    }

    public List<Event> findByTitle(String title){
        return eventRepository.findByTitle(title);
    }

    public Optional<Event> findById(Long id){
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

    public EventDTO createEvent(EventDTO eventDTO) {
		Event event = toDomain(eventDTO);
		eventRepository.save(event);
		return toDTO(event);
	}

    public EventDTO replaceEvent(Long id, EventDTO updatedEventDTO) {
		if (eventRepository.existsById(id)) {
			Event updatedEvent = toDomain(updatedEventDTO);
			updatedEvent.setId(id);
			eventRepository.save(updatedEvent);
			return toDTO(updatedEvent);
		} else {
			throw new NoSuchElementException();
		}
	}

    public EventDTO deleteEvent(Long id) {
		Event event = eventRepository.findById(id).orElseThrow();
		eventRepository.deleteById(id);
		return toDTO(event);
	}

    public void createEventImage(Long id, URI location, InputStream inputStream, Long size) {
		Event event = eventRepository.findById(id).orElseThrow();
		event.setImage(location.toString());
		event.setImageFile(BlobProxy.generateProxy(inputStream, size));
		eventRepository.save(event);
	}

    public Resource getEventImage(Long id) throws SQLException {
		Event event = eventRepository.findById(id).orElseThrow();
		if (event.getImageFile() != null) {
			return new InputStreamResource(event.getImageFile().getBinaryStream());
		} else {
			throw new NoSuchElementException();
		}
	}

    public EventDTO toDTO(Event event){
		return eventMapper.ToDTO(event);
	}

	public Event toDomain(EventDTO eventDTO){
		return eventMapper.ToDomain(eventDTO);
	}

	public Collection<EventDTO> toDTOs(Collection<Event> events){
		return eventMapper.ToDTOs(events);
	}

}
