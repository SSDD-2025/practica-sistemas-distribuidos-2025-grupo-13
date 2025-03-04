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



@Controller
public class ClientController {
    @Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private TicketRepository ticketRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private EventRepository eventRepository;
    
    
    private Boolean isLogged = false;
    private Boolean isAdmin = false;
    
	@PostConstruct
	public void init() {
        // Create a client
        Client client = new Client("John", "Doe", "johndoe@urjc.es", "123");
        Client client2 = new Client("Harry", "Potter", "potterharry@urjc.es", "321");
        Client client3 = new Client("Barry", "Allen", "b.allen@urjc.es", "111");
        Client admn = new Client("admin", "1", "admin@urjc.es", "admin");
        clientRepository.save(admn);
        clientRepository.save(client);
        clientRepository.save(client2);
        clientRepository.save(client3);
    }

    @PostMapping("/sign-up")
    public String signUp(@RequestParam String name, 
                        @RequestParam String lastName, 
                        @RequestParam String email, 
                        @RequestParam String password,
                        Model model) {
        
        // Checks if the client exists
        Optional<Client> existingClient = clientRepository.findByEmail(email);
        
        if (existingClient.isPresent()) {
            model.addAttribute("errorMessage", "Este correo ya existe. Por favor, emplea otro");
            return "error";
        }

        Client client = new Client(name, lastName, email, password);
        clientRepository.save(client);
        return "/registroSesion";
    }

    @PostMapping("/sign-in")
    public String signIn(HttpSession session, @RequestParam String email) {
        Optional<Client> clientLogin = clientRepository.findByEmail(email);
        System.out.println(clientLogin.get().getEmail());
        if (clientLogin.isPresent()) {
            session.setAttribute("client", clientLogin.get());
            isLogged = true;
            isAdmin = clientLogin.get().isAdmin();
            return "/inicioSesion";
        } else {
            return "/error";
        }
    }

    @PostMapping("/saveTicket")
    public String saveTicket(HttpSession session, @RequestParam Long eventID) {
        Client sessionclient = (Client) session.getAttribute("client");
        if (sessionclient == null) {
            return "/comprarSinCuenta"; // If there is no client redirect to error
        }
        System.out.println("Correo de la sesion del cliente"+sessionclient.getEmail());
        // Find client un bbdd if not will give an error
        Client client = clientRepository.findById(sessionclient.getId()).orElse(null);
        if (client == null) {
            return "/error"; // If no client is in session, redirect to error
        }
        System.out.println("Correo del cliente"+client.getEmail());

        Ticket ticket = ticketRepository.findById(eventID).orElse(null); // Gets the event from the eventID
        if (ticket == null) {
            return "/error"; // If there is no event, redirect to error
        }
        System.out.println("Titulo del ticket"+ticket.getTitle());

        client.getTickets().add(ticket);  // Associate the ticket with client
        clientRepository.save(client);   // Save the client with the ticket added

        return "ticketComprado";
    }
    
    @GetMapping("/data")
    public String getMyTickets(HttpSession session, Model model) {
        Client sessionclient = (Client) session.getAttribute("client");
        if (sessionclient == null) {
            return "/createCuenta"; // If no client is in session, redirect to error
        }
        System.out.println("Correo de la sesion del cliente"+sessionclient.getEmail());
        // Must find the client in the repository, otherwise will give an error
        Client client = clientRepository.findById(sessionclient.getId()).orElse(null);
        if (client == null) {
            return "/error"; // If no client is in session, redirect to error
        }
        System.out.println("Correo del cliente"+client.getEmail());

        List<Ticket> tickets = client.getTickets();
        List<Comment> comments = client.getComments();
        model.addAttribute("tickets", tickets);
        model.addAttribute("comments", comments);
        return "misdatos";
    }

    @GetMapping("/profile")
    public String profileLink(HttpSession session, Model model) {
        if(!isLogged && !isAdmin)return "/profile";
        else if(isLogged && isAdmin){
            model.addAttribute("client", session.getAttribute("client"));
            model.addAttribute("comment", commentRepository.findAll());
            model.addAttribute("event", eventRepository.findAll());
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
        return "/index";
    }

}
