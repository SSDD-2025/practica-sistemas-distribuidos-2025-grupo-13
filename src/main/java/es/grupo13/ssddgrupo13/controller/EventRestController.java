package es.grupo13.ssddgrupo13.controller;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.grupo13.ssddgrupo13.dto.EventDTO;
import es.grupo13.ssddgrupo13.repository.EventRepository;
import es.grupo13.ssddgrupo13.services.EventService;

@RestController
@RequestMapping("/api/events")
public class EventRestController {

    @Autowired
    private EventRepository eventRepository;
    
    
    @Autowired
    private EventService eventService;

    @GetMapping("/concerts/")
    public Page<EventDTO> getConcerts(Pageable pageable) {
    return  eventRepository.findByType("concierto",pageable).map(eventService::toDTO);
    }

    @GetMapping("/festivals/")
    public Page<EventDTO> getFestivals(Pageable pageable) {
        return  eventRepository.findByType("festival",pageable).map(eventService::toDTO);
    }

    @GetMapping("/clubbing/")
    public Page<EventDTO> getClubs(Pageable pageable) {
        return  eventRepository.findByType("club",pageable).map(eventService::toDTO);
    }

    @GetMapping("/")
    public Page<EventDTO> getAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable).map(eventService::toDTO);
    }

    @GetMapping("/{id}")
    public EventDTO getEvent(@PathVariable Long id) {
        return eventService.getEvent(id);
    }

    @PostMapping("/")
	public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDTO) {
        eventDTO = eventService.createEvent(eventDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(eventDTO.id()).toUri();
		return ResponseEntity.created(location).body(eventDTO);
	}

    @PutMapping("/{id}")
	public EventDTO replaceEvent(@PathVariable Long id, @RequestBody EventDTO updatedEventDTO) {
		return eventService.replaceEvent(id, updatedEventDTO);
	}

    @DeleteMapping("/{id}")
	public EventDTO deleteEvent(@PathVariable Long id) {
		return eventService.deleteEvent(id);
	}
    
    @GetMapping("/{id}/image")
	public ResponseEntity<Object> getEventImage(@PathVariable Long id) throws SQLException, IOException {
		Resource eventImage = eventService.getEventImage(id);
		return ResponseEntity
				.ok()
				.header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
				.body(eventImage);
	}

    @PostMapping("/{id}/image")
	public ResponseEntity<Object> createEventImage(@PathVariable Long id, @RequestParam MultipartFile imageFile) throws IOException {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		eventService.createEventImage(id, location, imageFile.getInputStream(), imageFile.getSize());
		return ResponseEntity.created(location).build();
	}

}
