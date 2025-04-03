package es.grupo13.ssddgrupo13.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import es.grupo13.ssddgrupo13.dto.EventDTO;
import es.grupo13.ssddgrupo13.model.Client;
import es.grupo13.ssddgrupo13.model.Event;
import es.grupo13.ssddgrupo13.model.Ticket;
import es.grupo13.ssddgrupo13.model.TicketStatus;
import es.grupo13.ssddgrupo13.services.ClientService;
import es.grupo13.ssddgrupo13.services.EventService;
import es.grupo13.ssddgrupo13.services.TicketService;
import es.grupo13.ssddgrupo13.utils.ImageUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Handles event management and ticket purchasing.
 */
@Controller
public class EventController {

    @Autowired private EventService eventService;
    @Autowired private TicketService ticketService;
    @Autowired private ClientService clientService;
    @Autowired private ImageUtils imageUtils;

    /**
     * Returns the image for a given event.
     */
    @GetMapping("/event-image/{id}")
    public ResponseEntity<Object> downloadImage(@PathVariable long id) throws SQLException {
        Optional<Event> event = eventService.findById(id);
        if (event.isPresent() && event.get().getImage() != null) {
            Blob image = event.get().getImageFile();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .body(new InputStreamResource(image.getBinaryStream()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Buys a ticket for a user if available.
     */
    @PostMapping("/buyTicket")
    public String buyTicket(Model model, HttpServletResponse response, @RequestParam Long eventID) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }

        Client client = extractClientFromPrincipal(auth.getPrincipal());
        if (client == null) return "/error";

        Event event = eventService.findById(eventID).orElse(null);
        if (event == null) return "/error";

        Ticket ticket = event.getTickets().stream()
                .filter(t -> t.getStatus() == TicketStatus.OPEN)
                .findFirst().orElse(null);

        if (ticket == null) return "/error";

        ticketService.buyTicket(client, ticket);

        model.addAttribute("title", "Ticket comprado");
        model.addAttribute("message", "¡Se ha comprado el ticket correctamente!");
        model.addAttribute("linkText", "Aceptar");
        model.addAttribute("linkUrl", "/");
        return "notification";
    }

    /**
     * Displays concert events.
     */
    @GetMapping("/concerts")
    public String showConcerts(HttpServletRequest request, Model model) {
        return showEventsByType("concierto", request, model, "conciertos", "concerts");
    }

    /**
     * Displays festival events.
     */
    @GetMapping("/festivals")
    public String showFestivals(HttpServletRequest request, Model model) {
        return showEventsByType("festival", request, model, "festivales", "festivals");
    }

    /**
     * Displays club events.
     */
    @GetMapping("/clubbing")
    public String showClubs(HttpServletRequest request, Model model) {
        return showEventsByType("club", request, model, "club", "clubbing");
    }

    private String showEventsByType(String type, HttpServletRequest request, Model model, String attr, String view) {
        model.addAttribute(attr, eventService.findByType(type));
        return view;
    }

    /**
     * Displays event detail page.
     */
    @GetMapping("/ticket/{id}")
    public String showEventDetailPage(HttpServletRequest request, Model model, @PathVariable long id) {
        Optional<Event> optionalEvent = eventService.findById(id);
        if (optionalEvent.isEmpty()) return "error";

        Event event = optionalEvent.get();
        model.addAttribute("event", event);
        model.addAttribute("comment", event.getComments());
        return "eventDetailPage";
    }

    /**
     * Displays new event creation form.
     */
    @GetMapping("/newEvent")
    public String getNewEvent() {
        return "newEvent";
    }

    /**
     * Handles new event creation.
     */
    @PostMapping("/addNewEvent")
    public String addNewEvent(EventDTO eventDTO, @RequestParam MultipartFile image, Model model) {

        Blob imageBlob;
        try {
            imageBlob = (image != null && !image.isEmpty()) ?
                    new SerialBlob(image.getBytes()) : imageUtils.loadDefaultImage();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return "error";
        }

        Event event = eventService.toDomain(eventDTO);
        event.setImageFile(imageBlob);
        eventService.save(event);

        for (int i = 0; i < 10; i++) {
            Ticket ticket = new Ticket(event.getTitle(), event.getPrice(), event.getTimeFinish(), TicketStatus.OPEN, event);
            ticketService.save(ticket);
            event.getTickets().add(ticket);
        }

        eventService.save(event);

        model.addAttribute("title", "Evento creado");
        model.addAttribute("message", "¡El evento se ha creado con éxito!");
        model.addAttribute("linkText", "Aceptar");
        model.addAttribute("linkUrl", "/admin/");
        return "notification";
    }

    /**
     * Utility method to extract authenticated client.
     */
    private Client extractClientFromPrincipal(Object principal) {
        if (principal instanceof Client client) return client;
        if (principal instanceof UserDetails userDetails)
            return clientService.findByEmail(userDetails.getUsername()).orElse(null);
        return null;
    }
}
