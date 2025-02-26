package es.grupo13.ssddgrupo13.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.grupo13.ssddgrupo13.entities.Event;
import es.grupo13.ssddgrupo13.repository.CommentRepository;
import es.grupo13.ssddgrupo13.repository.EventRepository;
import es.grupo13.ssddgrupo13.repository.TicketRepository;
import jakarta.annotation.PostConstruct;

@Controller
public class EventController {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private  TicketRepository ticketRepository;

    @Autowired
    private CommentRepository commentRepository;

    @PostConstruct
	public void init() {
        // Create a event
        LocalDateTime start = LocalDateTime.of(2025, 02, 26, 23, 00, 00);
        LocalDateTime finish = LocalDateTime.of(2025, 02, 27, 06, 00, 00);

        Event shoko = new Event("SHOKO", 
                                200,
                                "Largas noches de música latina acompañado de buen ambiente",
                                start, 
                                finish,
                                "Sala Shoko Madrid",
                                "club");

        eventRepository.save(shoko);
        
        Event ohmyclub = new Event("OH MY CLUB", 
                                200,
                                "Descubre un nuevo universo con el reservado ambiente de nustra discoteca",
                                start, 
                                finish,
                                "C/Rosario Pino 14",
                                "club");
        
        eventRepository.save(ohmyclub);

        Event liberata = new Event("LIBERATA", 
                                200,
                                "Música de ambienta acompañada de gente dispuesta a pasarselo bien",
                                start, 
                                finish,
                                "El Corral De Chamartin",
                                "club");
        
        eventRepository.save(liberata);

        
    }

    @GetMapping("/clubbing")
    public String showClubs(Model model) {
        List<Event> clubs = eventRepository.findByType("club"); // Obtiene los eventos de la BD
        model.addAttribute("section_2", clubs); // Agrega la lista al modelo
        return "section_2"; // Nombre de la plantilla (sin .html)
    }

    
}
