package es.grupo13.ssddgrupo13.entities;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;

@Entity
public class Event {
    
	@Lob
	@Column(nullable = false)
 	private Blob imageFile;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false, length = 5000)
    private String description;

	@Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private LocalDateTime timeStart;

    @Column(nullable = false)
    private LocalDateTime timeFinish;

	@Column(nullable = false)
    private String type;

	@Column(nullable = false)
    private Integer precio;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    protected Event() {

    }

    public Event(String title, int capacity, String description, LocalDateTime timeStart, LocalDateTime timeFinish, String address, String type, Integer precio, Blob imageFile) {
        this.title = title;
        this.capacity = capacity;
        this.description = description;
        this.timeStart = timeStart;
        this.timeFinish = timeFinish;
		this.address = address;
		this.type = type;
		this.precio = precio;
		
		this.imageFile = imageFile;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}

	public void setImage(Blob imageFile) {
		
		this.imageFile = imageFile ;
		
	}

	public Blob getImage() {
		return imageFile;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAdress() {
		return address;
	}

	public void setAdress(String address) {
		this.address = address;
	}

    public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public LocalDateTime getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(LocalDateTime timeStart) {
		this.timeStart = timeStart;
	}

    public LocalDateTime getTimeFinish() {
		return timeFinish;
	}

	public void setTimeFinish(LocalDateTime timeFinish) {
		this.timeFinish = timeFinish;
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
