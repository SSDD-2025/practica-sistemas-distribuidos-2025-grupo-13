package es.grupo13.ssddgrupo13.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.grupo13.ssddgrupo13.model.Client;
import es.grupo13.ssddgrupo13.model.Comment;
import es.grupo13.ssddgrupo13.model.Event;
import es.grupo13.ssddgrupo13.model.Ticket;
import es.grupo13.ssddgrupo13.model.TicketStatus;
import es.grupo13.ssddgrupo13.repository.ClientRepository;
import es.grupo13.ssddgrupo13.services.ClientService;
import es.grupo13.ssddgrupo13.services.CommentService;
import es.grupo13.ssddgrupo13.services.EventService;
import es.grupo13.ssddgrupo13.services.TicketService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class ClientController {
    private final ClientService clientService;
    private final TicketService ticketService;
    private final CommentService commentService;
    private final EventService eventService;


    @Autowired
    private ClientRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ClientController(ClientService clientService, TicketService ticketService, CommentService commentService, EventService eventService) {
        this.clientService = clientService;
        this.ticketService = ticketService;
        this.commentService = commentService;
        this.eventService = eventService;
    }
    
    
    private Boolean isLogged = false;
    private Boolean isAdmin = false;
    

    @PostMapping("/sign-up")
    public String signUp(@RequestParam String name, 
                        @RequestParam String lastName, 
                        @RequestParam String email, 
                        @RequestParam String password,
                        Model model,
                        HttpServletRequest request) {
        
    
       if (userRepository.findByName(email).isPresent()) {
            return "redirect:/register?error=user_exists"; // Redirect error page
        }
        Client user = new Client(name, lastName, email, passwordEncoder.encode(password), Arrays.asList("USER"));
        userRepository.save(user);

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }

        // Make authentiucaion token
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, authorities);

        // Save in security context
        SecurityContextHolder.getContext().setAuthentication(auth);

        // Associate security context to session
        request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        return "redirect:/"; // Go to home page
    }

    @PostMapping("/sign-in")
    public String signIn(HttpSession session, @RequestParam String email) {
        Optional<Client> clientLogin = clientService.findByEmail(email);
        System.out.println(clientLogin.get().getEmail());
        if (clientLogin.isPresent()) {
            session.setAttribute("client", clientLogin.get());
            isLogged = true;
            isAdmin = clientLogin.get().isAdmin();
            return "/signIn";
        } else {
            return "/error";
        }
    }

    @PostMapping("/saveTicket")
    public String saveTicket(HttpSession session, @RequestParam Long eventID) {
        Client sessionclient = (Client) session.getAttribute("client");
        if (sessionclient == null) {
            return "/buyNoAccount"; // If there is no client redirect to error
        }
        System.out.println("Correo de la sesion del cliente"+sessionclient.getEmail());
        // Find client un bbdd if not will give an error
        Client client = clientService.findById(sessionclient.getId()).orElse(null);
        if (client == null) {
            return "/erro"; // If no client is in session, redirect to error
        }
        System.out.println("Correo del cliente"+client.getEmail());
        Event event = eventService.findById(eventID).orElse(null); // Gets the event from the eventID
        if (event == null) {
            return "/error"; // If there is no event, redirect to error
        }
        Ticket ticket = null;
        for (Ticket t : eventService.findById(eventID).get().getTickets()) {
            if (t.getStatus() == TicketStatus.OPEN) {
                ticket = t;
                break;
            }
            
        }
        if (ticket == null) {
            return "/error"; // If there is no ticket, redirect to error
        }
        
        
        System.out.println("Titulo del ticket"+ticket.getTitle());
        ticket.setStatus(TicketStatus.CLOSED); // Change the status of the ticket to closed
        client.getTickets().add(ticket);  // Associate the ticket with client
        clientService.save(client);   // Save the client with the ticket added
        ticketService.save(ticket);   // Save the ticket in the repository

        return "buyedTicket";
    }
    
    @GetMapping("/data")
    public String getMyTickets(HttpSession session, Model model) {
        Client sessionclient = (Client) session.getAttribute("client");
        if (sessionclient == null) {
            return "/createsAnAccount"; // If no client is in session, redirect to error
        }
        System.out.println("Correo de la sesion del cliente"+sessionclient.getEmail());
        // Must find the client in the repository, otherwise will give an error
        Client client = clientService.findById(sessionclient.getId()).orElse(null);
        if (client == null) {
            return "/error"; // If no client is in session, redirect to error
        }
        System.out.println("Correo del cliente"+client.getEmail());

        List<Ticket> tickets = client.getTickets();
        List<Comment> comments = client.getComments();
        model.addAttribute("tickets", tickets);
        model.addAttribute("comments", comments);
        return "myData";
    }

    @GetMapping("/profile")
    public String profileLink(HttpSession session, Model model) {
        if(!isLogged && !isAdmin)return "/profile";
        else if(isLogged && isAdmin){
            model.addAttribute("client", session.getAttribute("client"));
            model.addAttribute("comment", commentService.findAll());
            model.addAttribute("event", eventService.findAll());
            return "/profile_admin";
        }else{
            model.addAttribute("client", session.getAttribute("client"));
            return "/profile_out";
        }
    }
    
    @PostMapping("/log_out")
    public String logout(HttpSession session) {
        isLogged = false;
        isAdmin = false;
        session.invalidate();
        return "/loguedOut";
    }
    
    @GetMapping("/editprofilepage")
    public String editProfilePage(HttpSession session, Model model) {
        Client sessionclient = (Client) session.getAttribute("client");
        if (sessionclient == null) {
            return "/error"; // If no client is in session, redirect to error
        }
        model.addAttribute("client", sessionclient);
        return "editprofile";
    }

    @PostMapping("/edit-profile")
    public String editProfile(HttpSession session, @RequestParam("name") String name, @RequestParam("lastName") String lastName) {
        Client sessionclient = (Client) session.getAttribute("client");
        if (sessionclient == null) {
            return "/error"; // If no client is in session, redirect to error
        }
        System.out.println("Correo de la sesion del cliente"+sessionclient.getEmail());
        // Must find the client in the repository, otherwise will give an error
        Client client = clientService.findById(sessionclient.getId()).orElse(null);
        if (client == null) {
            return "/error"; // If no client is in session, redirect to error
        }
       
        client.setName(name);
        client.setLastName(lastName);
        sessionclient.setLastName(lastName);
        sessionclient.setName(name);
        
        
        clientService.save(client);
        return "/profile_edited";
    }
    

}
