package es.grupo13.ssddgrupo13.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.grupo13.ssddgrupo13.dto.ClientDTO;
import es.grupo13.ssddgrupo13.dto.ClientMapper;
import es.grupo13.ssddgrupo13.model.Client;
import es.grupo13.ssddgrupo13.model.Comment;
import es.grupo13.ssddgrupo13.model.Ticket;
import es.grupo13.ssddgrupo13.model.TicketStatus;
import es.grupo13.ssddgrupo13.repository.ClientRepository;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private CommentService commentService;

    private List<Comment> comments;

    public Optional<Client> findById(long id){
        return clientRepository.findById(id);
    }

    public Optional<Client> findByEmail(String email){
        return clientRepository.findByEmail(email);
    }

    public Client save(Client client){
        return clientRepository.save(client);
    }

    public Collection<ClientDTO> getAllClients(){
        return toDTOs(clientRepository.findAll());
    }

    public ClientDTO getEvent(long id){
        return toDTO(clientRepository.findById(id).orElseThrow());
    }

    public ClientDTO createClient(ClientDTO clientDTO) {
		Client client = toDomain(clientDTO);
		clientRepository.save(client);
		return toDTO(client);
	}

    public ClientDTO replaceClient(long id, ClientDTO updatedClientDTO) {
		if (clientRepository.existsById(id)) {
			Client updatedClient = toDomain(updatedClientDTO);
			updatedClient.setId(id);
			clientRepository.save(updatedClient);
			return toDTO(updatedClient);
		} else {
			throw new NoSuchElementException();
		}
	}

    public ClientDTO deleteClient(long id){
        Client client = clientRepository.findById(id).orElseThrow();
        detachAndDelete(client);
		clientRepository.delete(client);
		return toDTO(client);
    }
    
    public ClientDTO toDTO(Client client){
		return clientMapper.ToDTO(client);
	}

	public Client toDomain(ClientDTO clientDTO){
		return clientMapper.ToDomain(clientDTO);
	}

	public Collection<ClientDTO> toDTOs(Collection<Client> clients){
		return clientMapper.ToDTOs(clients);
	}

    public void detachAndDelete(Client client) {
        List<Comment> comments = new ArrayList<>(client.getComments());
        for (Comment comment : comments) {
            commentService.detachAndDelete(comment.getId());
        }
    
        List<Ticket> tickets = new ArrayList<>(client.getTickets()); 
        for (Ticket ticket : tickets) {
            ticket.setStatus(TicketStatus.OPEN);
        }
    
        client.getTickets().clear();
    }
    

}
