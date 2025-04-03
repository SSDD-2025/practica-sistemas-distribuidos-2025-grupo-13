package es.grupo13.ssddgrupo13.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.grupo13.ssddgrupo13.dto.TicketDTO;
import es.grupo13.ssddgrupo13.repository.TicketRepository;
import es.grupo13.ssddgrupo13.services.TicketService;


@RestController
@RequestMapping("/api/tickets")
public class TicketRestController {
    @Autowired
    private TicketRepository ticketRepository;
    
    @Autowired
    TicketService ticketService;

    

    @GetMapping("/")
    public Page<TicketDTO> getAllTickets(Pageable pageable){
        return ticketRepository.findAll(pageable).map(ticketService::toDTO);
    }

    @GetMapping("/{id}")
    public TicketDTO getAllTickets(@PathVariable long id){
        return ticketService.getTicket(id);
    }

    @PostMapping("/")
	public ResponseEntity<TicketDTO> createTicket(@RequestBody TicketDTO ticketDTO) {
        ticketDTO = ticketService.createTicket(ticketDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(ticketDTO.id()).toUri();
		return ResponseEntity.created(location).body(ticketDTO);
	}

    @PutMapping("/{id}")
	public TicketDTO replaceTicket(@PathVariable Long id, @RequestBody TicketDTO updatedTicketDTO) {
		return ticketService.replacePost(id, updatedTicketDTO);
	}

    @DeleteMapping("/{id}")
    public TicketDTO deleteTicket(@PathVariable long id){
        return ticketService.deleteTicket(id);
    }

}
