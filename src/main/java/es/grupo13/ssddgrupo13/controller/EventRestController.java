package es.grupo13.ssddgrupo13.controller;

import java.net.URI;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.grupo13.ssddgrupo13.dto.EventDTO;
import es.grupo13.ssddgrupo13.services.EventService;

@RestController
public class EventRestController {
    
    @Autowired
    private EventService eventService;

    @GetMapping("/concerts/")
    public Collection<EventDTO> getConcerts() {
        return eventService.getConcerts();
    }

    @GetMapping("/festivals/")
    public Collection<EventDTO> getFestivals() {
        return eventService.getFestivals();
    }

    @GetMapping("/clubbing/")
    public Collection<EventDTO> getClubs() {
        return eventService.getClubs();
    }

    @GetMapping("/events/")
    public Collection<EventDTO> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/events/{id}")
    public EventDTO getEvent(@PathVariable long id) {
        return eventService.getEvent(id);
    }

    @PostMapping("/events/")
	public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDTO) {
        eventDTO = eventService.createEvent(eventDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(eventDTO.id()).toUri();
		return ResponseEntity.created(location).body(eventDTO);
	}

    @DeleteMapping("/events/{id}")
	public EventDTO deleteEvent(@PathVariable long id) {
		return eventService.deleteEvent(id);
	}

}
