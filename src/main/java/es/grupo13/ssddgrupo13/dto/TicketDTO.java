package es.grupo13.ssddgrupo13.dto;

import java.time.LocalDateTime;

import es.grupo13.ssddgrupo13.model.TicketStatus;

public record TicketDTO(Long id, String title, float price, LocalDateTime createdAt, TicketStatus status) {
}

