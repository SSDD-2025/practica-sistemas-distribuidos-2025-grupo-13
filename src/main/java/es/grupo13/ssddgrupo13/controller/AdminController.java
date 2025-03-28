package es.grupo13.ssddgrupo13.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.grupo13.ssddgrupo13.model.Client;
import es.grupo13.ssddgrupo13.model.Comment;
import es.grupo13.ssddgrupo13.model.Event;
import es.grupo13.ssddgrupo13.services.ClientService;
import es.grupo13.ssddgrupo13.services.CommentService;
import es.grupo13.ssddgrupo13.services.EventService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/")
/**
 * This controller is responsible for handling admin actions such as deleting events and comments.
 * It provides endpoints to delete events and comments from the system.
 */
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
        this.jdbcTemplate = new JdbcTemplate();
    }
    
   

    
    @DeleteMapping("/deleteEvent/{eventID}")
    public String deleteEvent(@RequestParam Long eventID) {
        eventService.deleteById(eventID);
        return "/eventRemoved";
    }

    @DeleteMapping("/delete_comment/{commentID}")
    public String deleteComment(@RequestParam Long commentID) {
        commentService.deleteById(commentID);
        return "/commentRemoved";
    }

    /**
     * Loads the admin editing page.
     */
    @GetMapping("/")
    public String adminPage(HttpServletRequest request, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isUserLogged = authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String);
        model.addAttribute("isUserLogged", isUserLogged);
        if (!isUserLogged) {
            return "redirect:/login";
        } else {
            Object principal = authentication.getPrincipal();
            Client client = null;
            if (principal instanceof Client) {
                client = (Client) principal;
            } else if (principal instanceof UserDetails) {
                String email = ((UserDetails) principal).getUsername();
                client = clientService.findByEmail(email).orElseThrow(); // <-- Asume que tienes esto
            }
            model.addAttribute("isAdmin", request.isUserInRole("ADMIN"));
            model.addAttribute("userLogged", client);
        }
        
        List<Event> events = (List<Event>) eventService.findAll();
        List<Comment> comments = (List<Comment>) commentService.findAll();
        model.addAttribute("comment", comments);
        model.addAttribute("event", events);
        return "/profile_admin";
    }
}
