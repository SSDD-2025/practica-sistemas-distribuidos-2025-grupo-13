package es.grupo13.ssddgrupo13.dto;

import java.util.List;

public record ClientDTO(
        Long id, 
        String name, 
        String lastName, 
        String email, 
        String password,
        List<String> roles) {
}    
