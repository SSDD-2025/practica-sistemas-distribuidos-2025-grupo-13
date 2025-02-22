package es.grupo13.ssddgrupo13.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String surname;
    private String password;

    @Email(message = "The email has to be valid")
    @NotBlank(message = "The email cannot be empty")
    @Column(unique = true, nullable = false)
    private String email;
    
    

}
