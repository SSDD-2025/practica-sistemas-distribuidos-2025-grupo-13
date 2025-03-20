package es.grupo13.ssddgrupo13.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import es.grupo13.ssddgrupo13.entities.Client;
import es.grupo13.ssddgrupo13.entities.Comment;
import es.grupo13.ssddgrupo13.entities.Event;
import es.grupo13.ssddgrupo13.entities.Ticket;
import es.grupo13.ssddgrupo13.entities.TicketStatus;
import es.grupo13.ssddgrupo13.services.ClientService;
import es.grupo13.ssddgrupo13.services.CommentService;
import es.grupo13.ssddgrupo13.services.EventService;
import es.grupo13.ssddgrupo13.services.TicketService;
import es.grupo13.ssddgrupo13.utils.ImageUtils;
import jakarta.servlet.http.HttpSession;


@Controller
public class EventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private  TicketService ticketService;

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

    @GetMapping("/clubbing")
    public String showClubs(Model model) {
        List<Event> clubs = eventService.findByType("club"); // Obtain the clubs from the database
        model.addAttribute("club", clubs); // Add the list to the model
        return "clubbing"; // Name of the template with out .html
        
    }

    @GetMapping("/concerts")
    public String showConciertos(Model model) {
        List<Event> concerts = eventService.findByType("concierto"); // Obtain the concerts from the database
        model.addAttribute("conciertos", concerts); // Add the list to the model
        return "concerts"; // Name of the template with out .html
    }  

    @GetMapping("/festivals")
    public String showFestivales(Model model) {
        List<Event> festivals = eventService.findByType("festival"); // Obtain the festivals from the database
        model.addAttribute("festivales", festivals); // Add the list to the model
        return "festivals"; // Name of the template with out .html
    }

    @GetMapping("/ticket/{id}")
    public String showTicket(Model model, @PathVariable long id) {
        System.out.println("ID: " + id);
        Optional<Event> optionalEvent = eventService.findById(id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            System.out.println("Event: " + event.getTitle());
            model.addAttribute("comment", event.getComments());
            model.addAttribute("event", event);
            return "ticket";
        } else {
            return "error";
        }
    }

    @PostMapping("/comment_in")
    public String comment_in(HttpSession session, @RequestParam String text, @RequestParam String rating, @RequestParam Long eventID) {
        Client sessionclient = (Client) session.getAttribute("client");
        if (sessionclient == null) {
            return "/commentNoAcount"; // If there is no client redirect to error
        }
        System.out.println("Correo de la sesion del cliente"+sessionclient.getEmail());
        // Must find the client in the repository, otherwise will give an error
        Client client = clientService.findById(sessionclient.getId()).orElse(null);
        if (client == null) {
            return "/error"; // If there is no client redirect to error
        }
        System.out.println("Correo del cliente"+client.getEmail());
        
        Event event = eventService.findById(eventID).orElse(null); // Obtain the event with the eventID
        
        if (event == null) {
            return "/error"; // If the event is not found send an error
        }
        
        Comment comment = new Comment(client.getName(), text, Integer.valueOf(rating), event.getTitle());
        event.getComments().add(comment);  // Associate the comment with the event
        client.getComments().add(comment); // Associate the comment with the client
        commentService.save(comment);   // Save the comment in the repository
        eventService.save(event);       // Save the event with the comment added
        clientService.save(client);     // Save the client with the comment added

        return "redirect:/ticket/" + eventID;  // Redirect to the event page
    }

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
