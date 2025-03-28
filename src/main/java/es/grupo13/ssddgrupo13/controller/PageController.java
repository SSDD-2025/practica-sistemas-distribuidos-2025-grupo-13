package es.grupo13.ssddgrupo13.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import es.grupo13.ssddgrupo13.model.Client;
import es.grupo13.ssddgrupo13.model.Comment;
import es.grupo13.ssddgrupo13.model.Event;
import es.grupo13.ssddgrupo13.model.Ticket;
import es.grupo13.ssddgrupo13.services.ClientService;
import es.grupo13.ssddgrupo13.services.EventService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PageController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private EventService eventService;

    @ModelAttribute
    public void addAttributes(Model model, HttpServletRequest request) {
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
            model.addAttribute("isAdmin", request.isUserInRole("ADMIN"));
            model.addAttribute("userLogged", client);
        }
    }
    
    @GetMapping("/")
    public String indexForm(Model model, HttpServletRequest request) {
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
            model.addAttribute("isAdmin", request.isUserInRole("ADMIN"));
            model.addAttribute("userLogged", client);
        }

        return "index";
    }

    // Controller method to go to the profile page
    @GetMapping("/profilePage")
    public String profile(HttpServletRequest request, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("Autenticación: " + authentication);
        System.out.println("Principal: " + authentication.getPrincipal());
        System.out.println("¿Autenticado?: " + authentication.isAuthenticated());

        boolean isUserLogged = authentication.isAuthenticated();
        model.addAttribute("isUserLogged", isUserLogged);

        if (isUserLogged) {
            Object principal = authentication.getPrincipal();
            Client user = null;

            if (principal instanceof Client) {
                user = (Client) principal;
            } else if (principal instanceof UserDetails) {
                String email = ((UserDetails) principal).getUsername();
                user = clientService.findByEmail(email).orElse(null); 
            }

            model.addAttribute("userLogged", user);
            model.addAttribute("client", user); 
            
            Client managedClient = clientService.findByEmail(user.getEmail()).orElse(null);

            //Logic to add my comments and my tickets
            List<Ticket> myTickets = managedClient.getTickets();
            List<Comment> myComments = managedClient.getComments();

            model.addAttribute("tickets", myTickets);
            model.addAttribute("comments", myComments);

            model.addAttribute("isAdmin", request.isUserInRole("ADMIN"));
           

            System.out.println("Usuario autenticado: " + (user != null));
            System.out.println("Redirigiendo a profileTest");
           
        }

        return "profile";
    }


    //Clubbing method to show the clubs, used to be in the EventController
    @GetMapping("/clubbing")
    public String clubbingRedirection(HttpServletRequest request, Model model) {
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

            model.addAttribute("isAdmin", request.isUserInRole("ADMIN"));
            model.addAttribute("userLogged", client);
        }

        List<Event> clubs = eventService.findByType("club");
        model.addAttribute("club", clubs);
        return "clubbing"; 
        
    }

    @GetMapping("/contact")
    public String contactanosLink(HttpServletRequest request, Model model) {
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
            model.addAttribute("isAdmin", request.isUserInRole("ADMIN"));
            model.addAttribute("userLogged", client);
        }
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