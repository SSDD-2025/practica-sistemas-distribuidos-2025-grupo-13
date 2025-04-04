package es.grupo13.ssddgrupo13.services;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.grupo13.ssddgrupo13.dto.TicketDTO;
import es.grupo13.ssddgrupo13.dto.TicketMapper;
import es.grupo13.ssddgrupo13.model.Client;
import es.grupo13.ssddgrupo13.model.Ticket;
import es.grupo13.ssddgrupo13.model.TicketStatus;
import es.grupo13.ssddgrupo13.repository.TicketRepository;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private ClientService clientService;

    public List<Ticket> findByTitle(String title) {
        return ticketRepository.findByTitle(title);
    }

    public Optional<Ticket> findById(Long id) {
        return ticketRepository.findById(id);
    }

    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public TicketDTO createTicket(TicketDTO ticketDTO) {
        Ticket ticket = toDomain(ticketDTO);
        ticketRepository.save(ticket);
        return toDTO(ticket);
    }

    public TicketDTO replaceTicket(Long id, TicketDTO updatedTicketDTO) {
        if (ticketRepository.existsById(id)) {
            Ticket updatedTicket = toDomain(updatedTicketDTO);
            updatedTicket.setId(id);
            ticketRepository.save(updatedTicket);
            return toDTO(updatedTicket);
        } else {
            throw new NoSuchElementException();
        }
	}

    public TicketDTO deleteTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow();
        detachAndDelete(id);
        ticketRepository.deleteById(id);
        return toDTO(ticket);
    }

    @Transactional
    public void buyTicket(Client client, Ticket ticket) {
        Client managedClient = clientService.findById(client.getId())
            .orElseThrow(() -> new RuntimeException("Client not found"));
        ticket.setStatus(TicketStatus.CLOSED);
        ticket.setClient(client);
        managedClient.getTickets().add(ticket);
        clientService.save(managedClient);
        save(ticket);
    }

    public Collection<TicketDTO> getAllTickets(){
        return toDTOs(ticketRepository.findAll());
    }

    public TicketDTO getTicket(Long id){
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

    @Transactional
    public void detachAndDelete(Long id){
        Ticket ticket = ticketRepository.findById(id).orElseThrow();
        ticket.getEvent().getTickets().remove(ticket);
        if (ticket.getStatus().equals(TicketStatus.CLOSED)){
            ticket.getClient().getTickets().remove(ticket);
        }
    }
}
