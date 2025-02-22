package es.grupo13.ssddgrupo13.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @Pattern(regexp = "^[a-zA-Z]+$", message = "The name can only have letters")
    private String name;

    @Column(nullable = false)
    @Pattern(regexp = "^[a-zA-Z]+$", message = "The last name can only have letters")
    private String lastName;

    @Column(nullable = false)
    private String password;

    @Email(message = "The email has to be valid")
    @NotBlank(message = "The email cannot be empty")
    @Column(unique = true, nullable = false)
    private String email;
    
    @OneToMany
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    protected Client() {

    }

    public Client(String name, String lastName, String email, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

}
