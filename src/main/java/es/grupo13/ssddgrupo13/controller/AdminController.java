// --- AdminController.java ---
package es.grupo13.ssddgrupo13.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import es.grupo13.ssddgrupo13.model.Client;
import es.grupo13.ssddgrupo13.model.Comment;
import es.grupo13.ssddgrupo13.model.Event;
import es.grupo13.ssddgrupo13.services.ClientService;
import es.grupo13.ssddgrupo13.services.CommentService;
import es.grupo13.ssddgrupo13.services.EventService;
import jakarta.servlet.http.HttpServletRequest;

/**
 * AdminController handles actions exclusive to administrators such as
 * viewing all events and comments and deleting them.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final EventService eventService;
    private final CommentService commentService;
    private final ClientService clientService;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AdminController(EventService eventService, CommentService commentService, ClientService clientService) {
        this.eventService = eventService;
        this.commentService = commentService;
        this.clientService = clientService;
        this.jdbcTemplate = new JdbcTemplate(); // Unused, could be removed
    }

    /**
     * Deletes an event by its ID.
     */
    @PostMapping("/deleteEvent/{eventID}")
    public String deleteEvent(@PathVariable Long eventID) {
        System.out.println("Attempting to delete event with ID: " + eventID);
        eventService.deleteById(eventID);
        return "eventRemoved";
    }

    /**
     * Deletes a comment by its ID.
     */
    @PostMapping("/deleteComment/{commentID}")
    public String deleteComment(@RequestParam Long commentID) {
        commentService.deleteById(commentID);
        return "commentRemoved";
    }

    /**
     * Loads the admin dashboard page with all events and comments.
     */
    @GetMapping("/")
    public String adminPage(HttpServletRequest request, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isUserLogged = auth != null && auth.isAuthenticated()
                && !(auth.getPrincipal() instanceof String);

        model.addAttribute("isUserLogged", isUserLogged);
        if (!isUserLogged) {
            return "redirect:/login";
        }

        Client client = extractClientFromPrincipal(auth.getPrincipal());
        model.addAttribute("isAdmin", request.isUserInRole("ADMIN"));
        model.addAttribute("userLogged", client);

        Iterable<Event> events = eventService.findAll();
        Iterable<Comment> comments = commentService.findAll();

        model.addAttribute("event", events);
        model.addAttribute("comment", comments);

        return "profile_admin";
    }

    /**
     * Utility method to extract a Client from the authenticated principal.
     */
    private Client extractClientFromPrincipal(Object principal) {
        if (principal instanceof Client client) {
            return client;
        } else if (principal instanceof UserDetails userDetails) {
            return clientService.findByEmail(userDetails.getUsername()).orElse(null);
        }
        return null;
    }
}
