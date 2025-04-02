package es.grupo13.ssddgrupo13.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.grupo13.ssddgrupo13.dto.TicketDTO;
import es.grupo13.ssddgrupo13.services.TicketService;


@RestController
@RequestMapping("/api/tickets")
public class TicketRestController {
    
    @Autowired
    TicketService ticketService;

    @GetMapping("/")
    public Collection<TicketDTO> getAllTickets(){
        return ticketService.getAllTickets();
    }

    @GetMapping("/{id}")
    public TicketDTO getAllTickets(@PathVariable long id){
        return ticketService.getTicket(id);
    }
    @DeleteMapping("/{id}")
    public TicketDTO deleteTicket(@PathVariable long id){

        return ticketService.deleteTicket(id);
    }
}
