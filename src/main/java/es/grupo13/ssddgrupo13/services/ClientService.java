package es.grupo13.ssddgrupo13.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.grupo13.ssddgrupo13.dto.ClientDTO;
import es.grupo13.ssddgrupo13.dto.ClientMapper;
import es.grupo13.ssddgrupo13.model.Client;
import es.grupo13.ssddgrupo13.model.Comment;
import es.grupo13.ssddgrupo13.model.Event;
import es.grupo13.ssddgrupo13.model.Ticket;
import es.grupo13.ssddgrupo13.repository.ClientRepository;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientMapper clientMapper;

    public Optional<Client> findById(Long id){
        return clientRepository.findById(id);
    }

    public Optional<Client> findByEmail(String email){
        return clientRepository.findByEmail(email);
    }

    public Optional<Client> findByName(String name){
        return clientRepository.findByName(name);
    }

    public Client save(Client client){
        return clientRepository.save(client);
    }

    public Collection<ClientDTO> getAllClients(){
        return toDTOs(clientRepository.findAll());
    }

    public ClientDTO getClient(Long id){
        return toDTO(clientRepository.findById(id).orElseThrow());
    }

    public ClientDTO createClient(ClientDTO clientDTO) {
		Client client = toDomain(clientDTO);
        String encodedPassword = passwordEncoder.encode(client.getEncodedPassword());
        client.setEncodedPassword(encodedPassword);
		clientRepository.save(client);
		return toDTO(client);
	}

    public ClientDTO replaceClient(Long id, ClientDTO updatedClientDTO) {
		if (clientRepository.existsById(id)) {
			Client updatedClient = toDomain(updatedClientDTO);
			updatedClient.setId(id);
			clientRepository.save(updatedClient);
			return toDTO(updatedClient);
		} else {
			throw new NoSuchElementException();
		}
	}

    public ClientDTO deleteClient(Long id){
        Client client = clientRepository.findById(id).orElseThrow();
		detachAndDelete(client);
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

    private void detachAndDelete(Client client) {
        for (Ticket ticket : new ArrayList<>(client.getTickets())) {
            Event event = ticket.getEvent();
            if (event != null) {
                event.getTickets().remove(ticket);
                ticket.setEvent(null);
            }
            ticket.setClient(null);
        }
        client.getTickets().clear();
        for (Comment comment : new ArrayList<>(client.getComments())) {
            Event event = comment.getEvent();
            if (event != null) {
                event.getComments().remove(comment);
                comment.setEvent(null);
            }
            comment.setClient(null);
        }
        client.getComments().clear();
        clientRepository.delete(client);
    }

}
