package es.grupo13.ssddgrupo13.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    public AdminController(EventService eventService, CommentService commentService, ClientService clientService) {
        this.eventService = eventService;
        this.commentService = commentService;
        new JdbcTemplate();
    }

    /**
     * Deletes an event by its ID.
     */
    @PostMapping("/deleteEvent/{eventID}")
    public String deleteEvent(@PathVariable Long eventID, Model model) {
        eventService.deleteById(eventID);

        model.addAttribute("title", "✅ OK");
        model.addAttribute("message", "¡Has borrado este evento correctamente!");
        model.addAttribute("linkText", "Aceptar");
        model.addAttribute("linkUrl", "/admin/");
        return "notification";
    }

    /**
     * Deletes a comment by its ID.
     */
    @PostMapping("/deleteComment/{commentID}")
    public String deleteComment(@RequestParam Long commentID, Model model) {
        commentService.deleteById(commentID);
        model.addAttribute("title", "✅ OK");
        model.addAttribute("message", "¡Has borrado este comentario correctamente!");
        model.addAttribute("linkText", "Aceptar");
        model.addAttribute("linkUrl", "/admin/");
        return "notification";
    }

    /**
     * Loads the admin dashboard page with all events and comments.
     */
    @GetMapping("/")
    public String adminPage(HttpServletRequest request, Model model) {
        Iterable<Event> events = eventService.findAll();
        Iterable<Comment> comments = commentService.findAll();

        model.addAttribute("event", events);
        model.addAttribute("comment", comments);

        return "profile_admin";
    }

}
