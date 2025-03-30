package es.grupo13.ssddgrupo13.dto;

import java.time.LocalDateTime;

import es.grupo13.ssddgrupo13.model.TicketStatus;

public class TicketDTO {
    public record TicketDTOs(long id,String title, float price, LocalDateTime createdAt, TicketStatus status) {
    }
}
