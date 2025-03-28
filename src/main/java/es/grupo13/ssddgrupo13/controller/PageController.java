package es.grupo13.ssddgrupo13.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import es.grupo13.ssddgrupo13.model.Client;
import es.grupo13.ssddgrupo13.model.Event;
import es.grupo13.ssddgrupo13.services.ClientService;
import es.grupo13.ssddgrupo13.services.EventService;

/**
 * Handles navigation between static and dynamic pages.
 */
@Controller
public class PageController {

    @Autowired private ClientService clientService;
    @Autowired private EventService eventService;

    @GetMapping("/")
    public String indexForm() {
        return "index";
    }

    @GetMapping("/profilePage")
    public String profile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated()) {
            model.addAttribute("title", "Acceso denegado");
            model.addAttribute("message", "Inicia sesión o regístrate para acceder a tu perfil.");
            model.addAttribute("linkText", "Aceptar");
            model.addAttribute("linkUrl", "/");
            return "notification";
        }
        Client user = extractClientFromPrincipal(auth.getPrincipal());
        if (user == null) return "error";

        Client managedClient = clientService.findByEmail(user.getEmail()).orElse(null);
        if (managedClient == null) return "error";

        model.addAttribute("client", managedClient);
        model.addAttribute("tickets", managedClient.getTickets());
        model.addAttribute("comments", managedClient.getComments());

        return "profile";
    }

    @GetMapping("/clubbing")
    public String clubbingRedirection(Model model) {
        List<Event> clubs = eventService.findByType("club");
        model.addAttribute("club", clubs);
        return "clubbing";
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
    public String contactRecievedLink(Model model) {
        model.addAttribute("title", "Pregunta recibida");
        model.addAttribute("message", "Tu pregunta ha sido recibida");
        model.addAttribute("linkText", "Aceptar");
        model.addAttribute("linkUrl", "/");
        return "notification";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/loginError")
    public String loginError() {
        return "redirect:/";
    }

    @GetMapping("/private")
    public String privateLink() {
        return "private";
    }

    /**
     * Utility method to extract a Client from principal.
     */
    private Client extractClientFromPrincipal(Object principal) {
        if (principal instanceof Client client) return client;
        if (principal instanceof UserDetails userDetails)
            return clientService.findByEmail(userDetails.getUsername()).orElse(null);
        return null;
    }
}
