package es.grupo13.ssddgrupo13.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.grupo13.ssddgrupo13.model.Client;
import es.grupo13.ssddgrupo13.model.Comment;
import es.grupo13.ssddgrupo13.model.Event;
import es.grupo13.ssddgrupo13.services.ClientService;
import es.grupo13.ssddgrupo13.services.CommentService;
import es.grupo13.ssddgrupo13.services.EventService;
import jakarta.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    ClientService clientService;

    @Autowired
    EventService eventService;

    @Autowired
    CommentService commentService;

    @PostMapping("/comment_in")
    public String comment_in(HttpSession session,
            @RequestParam String text,
            @RequestParam String rating,
            @RequestParam Long eventID) {

        // Check if the user is logged
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isUserLogged = authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String);

        if (!isUserLogged) {
            return "redirect:/login";
        }

        Object principal = authentication.getPrincipal();
        String email = "";
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else if (principal instanceof Client) {
            email = ((Client) principal).getEmail(); // Uses email if it's saved on user
        }

        Event event = eventService.findById(eventID).orElse(null); // Obtain the event with the eventID

        if (event == null) {
            return "/error"; // If the event is not found send an error
        }

        Client managedClient = clientService.findByEmail(email).orElse(null);

        Comment comment = new Comment(managedClient.getName(), text, Integer.valueOf(rating), event.getTitle());
        comment.setEvent(event);
        event.getComments().add(comment); // Associate the comment with the event
        managedClient.getComments().add(comment); // Associate the comment with the client
        commentService.save(comment); // Save the comment in the repository
        eventService.save(event); // Save the event with the comment added
        clientService.save(managedClient); // Save the client with the comment added

        return "redirect:/ticket/" + eventID; // Redirect to the event page
    }

    @PostMapping("/comment_out/{commentId}/{titleEvento}")
    public String eliminarComentario(Model model, HttpSession session, @PathVariable Long commentId,
            @PathVariable String titleEvento) {
       
        // Check if the user is logged
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isUserLogged = authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String);

        model.addAttribute("isUserLogged", isUserLogged);
        if (isUserLogged) {
            Object principal = authentication.getPrincipal();
            String email = "";
            Client client = null;

            if (principal instanceof UserDetails) {
                email = ((UserDetails) principal).getUsername();
                client = clientService.findByEmail(email).orElse(null);
            } else if (principal instanceof Client) {
                email = ((Client) principal).getEmail();
                client = ((Client) principal);
            }
            model.addAttribute("userLogged", client);

            //Check if the user is the owner of the comment
            Client managedClient = clientService.findByEmail(email).orElse(null);
            Comment comment = commentService.findById(commentId).orElse(null);
            if (comment == null) {
                return "/error"; 
            } if (!comment.getAutor().equals(managedClient.getName())) {
                return "/error";
            }
            Event event = eventService.findByTitle(titleEvento).getFirst();
            event.getComments().remove(comment);
            managedClient.getComments().remove(comment);
            commentService.delete(comment);
            clientService.save(managedClient);
            eventService.save(event);
        }

        return "/commentRemoved";
    }
}
