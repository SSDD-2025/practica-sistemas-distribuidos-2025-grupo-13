package es.grupo13.ssddgrupo13.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.grupo13.ssddgrupo13.entities.Client;
import es.grupo13.ssddgrupo13.entities.Comment;
import es.grupo13.ssddgrupo13.entities.Event;
import es.grupo13.ssddgrupo13.entities.Ticket;
import es.grupo13.ssddgrupo13.repository.ClientRepository;
import es.grupo13.ssddgrupo13.repository.CommentRepository;
import es.grupo13.ssddgrupo13.repository.TicketRepository;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class ClientController {
    @Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private TicketRepository ticketRepository;

    @Autowired
    private CommentRepository commentRepository;

    
	@PostConstruct
	public void init() {
        // Create a client
        Client client = new Client("John", "Doe", "johndoe@urjc.es", "123");
        clientRepository.save(client);
    }

    @PostMapping("/sign-up")
    public String signUp(@RequestParam String name, 
                        @RequestParam String lastName, 
                        @RequestParam String email, 
                        @RequestParam String password,
                        Model model) {
        
        // Comprueba si el email ya existe
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
            return "/inicioSesion";
        } else {
            return "/error";
        }
    }

    @PostMapping("/saveTicket")
    public String saveTicket(HttpSession session, @RequestParam Long eventID) {
        Client sessionclient = (Client) session.getAttribute("client");
        if (sessionclient == null) {
            return "/error"; // Si no hay cliente en sesión, redirigir a error
        }
        System.out.println("Correo de la sesion del cliente"+sessionclient.getEmail());
        // Importante encontrar el cliente en la base de datos porque si no da error
        Client client = clientRepository.findById(sessionclient.getId()).orElse(null);
        if (client == null) {
            return "/error"; // Si no hay cliente en sesión, redirigir a error
        }
        System.out.println("Correo del cliente"+client.getEmail());

        Ticket ticket = ticketRepository.findById(eventID).orElse(null); // Obtener el evento usando el ticketID
        if (ticket == null) {
            return "/error"; // Si no se encuentra el evento, redirigir a error
        }
        System.out.println("Titulo del ticket"+ticket.getTitle());

        client.getTickets().add(ticket);  // Asociar el ticket con el cliente
        clientRepository.save(client);   // Guardar el cliente con el ticket agregado

        return "ticketComprado";
    }
    
    @GetMapping("/data")
    public String getMyTickets(HttpSession session, Model model) {
        Client sessionclient = (Client) session.getAttribute("client");
        if (sessionclient == null) {
            return "/createCuenta"; // Si no hay cliente en sesión, redirigir a error
        }
        System.out.println("Correo de la sesion del cliente"+sessionclient.getEmail());
        // Importante encontrar el cliente en la base de datos porque si no da error
        Client client = clientRepository.findById(sessionclient.getId()).orElse(null);
        if (client == null) {
            return "/error"; // Si no hay cliente en sesión, redirigir a error
        }
        System.out.println("Correo del cliente"+client.getEmail());

        List<Ticket> tickets = client.getTickets();
        model.addAttribute("tickets", tickets);
        return "misdatos";
    }
    

}
