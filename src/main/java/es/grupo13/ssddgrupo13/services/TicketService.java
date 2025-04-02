package es.grupo13.ssddgrupo13.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.grupo13.ssddgrupo13.dto.TicketDTO;
import es.grupo13.ssddgrupo13.dto.TicketMapper;
import es.grupo13.ssddgrupo13.model.Client;
import es.grupo13.ssddgrupo13.model.Event;
import es.grupo13.ssddgrupo13.model.Ticket;
import es.grupo13.ssddgrupo13.model.TicketStatus;
import es.grupo13.ssddgrupo13.repository.TicketRepository;

@Service
public class TicketService {
    
    @Autowired 
    private ClientService clientService;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketMapper ticketMapper;

    List<Ticket> findByTitle(String title) {
        return ticketRepository.findByTitle(title);
    }

    public Optional<Ticket> findById(long id) {
        return ticketRepository.findById(id);
    }
    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Transactional
    public void buyTicket(Client client, Event event, Ticket ticket) {

        Client managedClient = clientService.findById(client.getId())
            .orElseThrow(() -> new RuntimeException("Client not found"));

        ticket.setStatus(TicketStatus.CLOSED);
        managedClient.getTickets().add(ticket);
        clientService.save(managedClient);
        save(ticket);
    }

    public Collection<TicketDTO> getAllTickets(){
        return toDTOs(ticketRepository.findAll());
    }

    public TicketDTO getTicket(long id){
        return toDTO(ticketRepository.findById(id).orElseThrow());
    }

    public TicketDTO toDTO(Ticket ticket){
		return ticketMapper.ToDTO(ticket);
	}

	public Ticket toDomain(TicketDTO ticketDTO){
		return ticketMapper.ToDomain(ticketDTO);
	}

	public Collection<TicketDTO> toDTOs(Collection<Ticket> ticket){
		return ticketMapper.ToDTOs(ticket);
	}

}
