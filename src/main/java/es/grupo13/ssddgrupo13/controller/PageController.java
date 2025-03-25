package es.grupo13.ssddgrupo13.controller;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import es.grupo13.ssddgrupo13.model.Client;
import es.grupo13.ssddgrupo13.model.Ticket;
import es.grupo13.ssddgrupo13.services.CommentService;
import es.grupo13.ssddgrupo13.services.EventService;
import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private EventService eventService;


    @GetMapping("/")
    public String indexForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isUserLogged = authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String);

        model.addAttribute("isUserLogged", isUserLogged);

        if (isUserLogged) {
            Object principal = null;
            if (authentication != null) {
                principal = authentication.getPrincipal();
            }
            String username = "";
            Client client = null;

            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else if (principal instanceof Client) {
                username = ((Client) principal).getEmail();
                client = ((Client) principal);
            }
            model.addAttribute("userLogged", client);
        }
        return "index";
    }

     // Controller method to go to the profile page
    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("Autenticación: " + authentication);
        System.out.println("Principal: " + authentication.getPrincipal());
        System.out.println("¿Autenticado?: " + authentication.isAuthenticated());

        boolean isUserLogged = authentication.isAuthenticated();
        model.addAttribute("isUserLogged", isUserLogged);

        if (isUserLogged) {
            Object principal = authentication.getPrincipal();
            String username = "";
            Client user = null;

            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else if (principal instanceof Client) {
                username = ((Client) principal).getEmail();
                user = ((Client) principal);
            }

            model.addAttribute("userLogged", user);
            model.addAttribute("client", session.getAttribute("client"));
            model.addAttribute("comment", commentService.findAll());
            model.addAttribute("event", eventService.findAll());
        }

        return "profile";
    }


    @GetMapping("/contact")
    public String contactanosLink() {
        return "contact";
    }

    @GetMapping("/register")
    public String registerLink() {
        return "register";
    }

    @PostMapping("/contact_recieved")
    public String contact_recievedLink() {
        return "contact_recieved";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/loginError")
    public String loginError() {
        return "/";
    }

    @GetMapping("/private")
    public String privateLink() {
        return "private";
    }
}