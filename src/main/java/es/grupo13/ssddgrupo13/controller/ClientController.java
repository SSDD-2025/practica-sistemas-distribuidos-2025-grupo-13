package es.grupo13.ssddgrupo13.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.grupo13.ssddgrupo13.entities.Client;
import es.grupo13.ssddgrupo13.repository.ClientRepository;
import es.grupo13.ssddgrupo13.repository.CommentRepository;
import es.grupo13.ssddgrupo13.repository.TicketRepository;
import jakarta.annotation.PostConstruct;


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

    @PostMapping("/home")
    public String singUp(@RequestParam String name, @RequestParam String lastName, @RequestParam String email, @RequestParam String password) {
        Client client = new Client(name, lastName, email, password);
        clientRepository.save(client);
        return "index";
    }
    
}
