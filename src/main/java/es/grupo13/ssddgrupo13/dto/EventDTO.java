package es.grupo13.ssddgrupo13.dto;

import java.time.LocalDateTime;

public record EventDTO(Long id, String title, 
        String description, LocalDateTime timeStart, 
        LocalDateTime timeFinish, String address, 
        String type, Integer price, String image) {
}  

