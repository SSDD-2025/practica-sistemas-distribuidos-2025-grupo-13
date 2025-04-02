package es.grupo13.ssddgrupo13.dto;

import java.sql.Blob;
import java.time.LocalDateTime;

public record EventDTO(long id, String title, String description,
                       LocalDateTime timeStart, LocalDateTime timeFinish, 
                       String address, String type, Integer precio, 
                       Blob imageFile) {
}  

