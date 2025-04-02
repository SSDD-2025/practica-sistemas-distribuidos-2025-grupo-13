package es.grupo13.ssddgrupo13.dto;

import org.mapstruct.Mapper;
import java.util.Collection;
import java.util.List;
import es.grupo13.ssddgrupo13.model.Ticket;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    TicketDTO ToDTO(Ticket ticket);
    Ticket ToDomain(TicketDTO ticketDTO);
    List<TicketDTO> ToDTOs(Collection<Ticket> tickets);
    List<Ticket> ToDomains(Collection<TicketDTO> ticketDTOs);
}
