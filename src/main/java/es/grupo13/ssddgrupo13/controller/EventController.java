package es.grupo13.ssddgrupo13.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
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

import es.grupo13.ssddgrupo13.model.Client;
import es.grupo13.ssddgrupo13.model.Comment;
import es.grupo13.ssddgrupo13.model.Event;
import es.grupo13.ssddgrupo13.model.Ticket;
import es.grupo13.ssddgrupo13.model.TicketStatus;
import es.grupo13.ssddgrupo13.services.ClientService;
import es.grupo13.ssddgrupo13.services.CommentService;
import es.grupo13.ssddgrupo13.services.EventService;
import es.grupo13.ssddgrupo13.services.TicketService;
import es.grupo13.ssddgrupo13.utils.ImageUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class EventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ImageUtils imageUtils;

    @GetMapping("/event-image/{id}")
    public ResponseEntity<Object> downloadImage(@PathVariable long id) throws SQLException {
        Optional<Event> op = eventService.findById(id);
        if (op.isPresent() && op.get().getImage() != null) {
            Blob image = op.get().getImage();
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .body(new InputStreamResource(op.get().getImage().getBinaryStream()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/buyTicket")
    public String buyTicket(Model model, HttpServletResponse response, @RequestParam Long eventID) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isUserLogged = authentication.isAuthenticated();
        if (authentication == null || !isUserLogged || authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/error"; // TODO USER NOT LOGGED IN
        }

        Object principal = authentication.getPrincipal();
        String username = "";
        Client client = null;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
            client = clientService.findByEmail(username).orElse(null);
        } else if (principal instanceof Client) {
            username = ((Client) principal).getEmail();
            client = ((Client) principal);
        }

        if (client == null) {
            return "/error"; // TODO USER NOT FOUND
        }

        Event event = eventService.findById(eventID).orElse(null);
        if (event == null) {
            return "/error";
        }
        Ticket ticket = null;

        for (Ticket t : eventService.findById(eventID).get().getTickets()) {
            if (t.getStatus() == TicketStatus.OPEN) {
                ticket = t;
                break;
            }

        }
        if (ticket == null) {
            return "/error";
        }

        ticketService.buyTicket(client, event, ticket);

        return "buyedTicket";
    }

    @GetMapping("/concerts")
    public String showConciertos(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isUserLogged = authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String);

        model.addAttribute("isUserLogged", isUserLogged);

        if (isUserLogged) {
            Object principal = authentication.getPrincipal();
            Client client = null;

            if (principal instanceof Client) {
                client = (Client) principal;
            } else if (principal instanceof UserDetails) {
                // Buscar el Client a partir del username
                String email = ((UserDetails) principal).getUsername();
                client = clientService.findByEmail(email).orElseThrow(); // <-- Asume que tienes esto
            }

            model.addAttribute("userLogged", client);
        }

        List<Event> concerts = eventService.findByType("concierto"); // Obtain the concerts from the database
        model.addAttribute("conciertos", concerts); // Add the list to the model
        return "concerts"; // Name of the template with out .html
    }

    @GetMapping("/festivals")
    public String showFestivales(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isUserLogged = authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String);

        model.addAttribute("isUserLogged", isUserLogged);

        if (isUserLogged) {
            Object principal = authentication.getPrincipal();
            Client client = null;

            if (principal instanceof Client) {
                client = (Client) principal;
            } else if (principal instanceof UserDetails) {
                // Buscar el Client a partir del username
                String email = ((UserDetails) principal).getUsername();
                client = clientService.findByEmail(email).orElseThrow(); // <-- Asume que tienes esto
            }

            model.addAttribute("userLogged", client);
        }
        List<Event> festivals = eventService.findByType("festival"); // Obtain the festivals from the database
        model.addAttribute("festivales", festivals); // Add the list to the model
        return "festivals"; // Name of the template with out .html
    }

    @GetMapping("/ticket/{id}")
    public String showEventDetailPage(Model model, @PathVariable long id) {
        System.out.println("ID: " + id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isUserLogged = authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String);

        model.addAttribute("isUserLogged", isUserLogged);

        if (isUserLogged) {
            Object principal = authentication.getPrincipal();
            Client client = null;

            if (principal instanceof Client) {
                client = (Client) principal;
            } else if (principal instanceof UserDetails) {
                // Buscar el Client a partir del username
                String email = ((UserDetails) principal).getUsername();
                client = clientService.findByEmail(email).orElseThrow(); // <-- Asume que tienes esto
            }

            model.addAttribute("userLogged", client);
        }

        Optional<Event> optionalEvent = eventService.findById(id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            System.out.println("Event: " + event.getTitle());
            model.addAttribute("comment", event.getComments());
            model.addAttribute("event", event);
            return "eventDetailPage";
        } else {
            return "error";
        }
    }

    //Created CommentController to handle the comments
    
    @PostMapping("/comment_out/{commentId}/{titleEvento}")
    public String eliminarComentario(HttpSession session, @PathVariable Long commentId, @PathVariable String titleEvento){
        Client sessionclient = (Client) session.getAttribute("client");
        Client client = clientService.findById(sessionclient.getId()).orElse(null);

        Comment comment = commentService.findById(commentId).orElse(null);

        Event event = eventService.findByTitle(titleEvento).getFirst();

        event.getComments().remove(comment);
        client.getComments().remove(comment);
        clientService.save(client);
        eventService.save(event);
        return "/commentRemoved";
    }

    @PostMapping("/delete_event")
    public String deleteEvent(@RequestParam Long eventID) {
        eventService.deleteById(eventID);
        return "/eventRemoved";
    }

    @PostMapping("/delete_comment")
    public String deleteComment(@RequestParam Long commentID) {
        // Delete all the references related with the comment in the event_comments
        jdbcTemplate.update("DELETE FROM event_comments WHERE comments_id = ?", commentID);

        // Delete the comment
        commentService.deleteById(commentID);
        return "/commentRemoved";
    }

    @GetMapping("/newEvent")
    public String getNewEvent() {
        return "newEvent";
    }
    @PostMapping("/addNewEvent")
    public String addNewEvent(@RequestParam String titleEvent, @RequestParam String description, @RequestParam String typeOptions, @RequestParam String timeStart, @RequestParam String timeEnd, @RequestParam String addressEvent, @RequestParam int priceEvent, @RequestParam MultipartFile image) {
        LocalDateTime startEvent = LocalDateTime.parse(timeStart);
        LocalDateTime finishEvent = LocalDateTime.parse(timeEnd);

        Blob imageBlob;
        try {
            // // Converts the MultipartFile to Blob if the image isn´t empty
            if (image != null && !image.isEmpty()) {
                imageBlob = new SerialBlob(image.getBytes());
            } else {
                imageBlob = imageUtils.loadDefaultImage();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return "error"; // Redirect to the error page
        }

        Event event = new Event(titleEvent, description, startEvent, finishEvent, addressEvent, typeOptions, priceEvent, imageBlob);
        eventService.save(event);

        for (int i = 0; i < 10; i++) {
            Ticket newTicket = new Ticket(event.getTitle(), event.getPrecio(), event.getTimeFinish(), TicketStatus.OPEN);
            ticketService.save(newTicket);
            event.getTickets().add(newTicket);
        }
        eventService.save(event);
        return "/createdEvent";
    }

}
