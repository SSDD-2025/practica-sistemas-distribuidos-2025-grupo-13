package es.grupo13.ssddgrupo13.dto;

import java.sql.Blob;
import java.time.LocalDateTime;

public class EventDTO {
    public record EventDTOs(long idString, String title, String description, LocalDateTime timeStart, LocalDateTime timeFinish, String address, String type, Integer precio, Blob imageFile) {
    }
    
}
