package es.grupo13.ssddgrupo13.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.grupo13.ssddgrupo13.entities.Client;
import es.grupo13.ssddgrupo13.entities.Comment;
import es.grupo13.ssddgrupo13.entities.Ticket;
import es.grupo13.ssddgrupo13.repository.ClientRepository;
import es.grupo13.ssddgrupo13.repository.CommentRepository;
import es.grupo13.ssddgrupo13.repository.EventRepository;
import es.grupo13.ssddgrupo13.repository.TicketRepository;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import services.ClientService;
import services.CommentService;
import services.EventService;
import services.TicketService;

@Controller
public class ClientController {
    @Autowired
	private ClientService clientService;
	
	@Autowired
	private TicketService ticketService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private EventService eventService;
    
    
    private Boolean isLogged = false;
    private Boolean isAdmin = false;
    

    @PostMapping("/sign-up")
    public String signUp(@RequestParam String name, 
                        @RequestParam String lastName, 
                        @RequestParam String email, 
                        @RequestParam String password,
                        Model model) {
        
        // Checks if the client exists
        Optional<Client> existingClient = clientService.findByEmail(email);
        
        if (existingClient.isPresent()) {
            model.addAttribute("errorMessage", "Este correo ya existe. Por favor, emplea otro");
            return "error";
        }

        Client client = new Client(name, lastName, email, password);
        clientService.save(client);
        return "/loggedIn";
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
            return "/error"; // If no client is in session, redirect to error
        }
        System.out.println("Correo del cliente"+client.getEmail());

        Ticket ticket = ticketService.findById(eventID).orElse(null); // Gets the event from the eventID
        if (ticket == null) {
            return "/error"; // If there is no event, redirect to error
        }
        System.out.println("Titulo del ticket"+ticket.getTitle());

        client.getTickets().add(ticket);  // Associate the ticket with client
        clientService.save(client);   // Save the client with the ticket added

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
    public String editProfilePage() {
        return "editprofile";
    }

    @PostMapping("/edit-profile")
    public String editProfile(HttpSession session, @RequestParam("name") String name, @RequestParam ("email") String email , @RequestParam("lastName") String lastName) {
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
        System.out.println("Correo del cliente"+client.getEmail());
        client.setName(name);
        client.setEmail(email);
        client.setEmail(email);
        sessionclient.setEmail(email);
        sessionclient.setLastName(lastName);
        sessionclient.setName(name);
        
        
        clientService.save(client);
        return "/profile_edited";
    }
    

}
