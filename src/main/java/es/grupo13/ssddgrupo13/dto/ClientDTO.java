package es.grupo13.ssddgrupo13.dto;

public class ClientDTO {
    public record ClientDTOs(long id, String name, String lastName, String email, String password) {
    }    
}
