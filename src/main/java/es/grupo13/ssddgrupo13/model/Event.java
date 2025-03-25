package es.grupo13.ssddgrupo13.model;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false, length = 5000)
    private String description;

	@Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private LocalDateTime timeStart;

    @Column(nullable = false)
    private LocalDateTime timeFinish;

	@Column(nullable = false)
    private String timeStartFormat;

    @Column(nullable = false)
    private String timeFinishFormat;

	@Column(nullable = false)
    private String type;

	@Column(nullable = false)
    private Integer precio;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    protected Event() {

    }

    public Event(String title, String description, LocalDateTime timeStart, LocalDateTime timeFinish, String address, String type, Integer precio, Blob imageFile) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy, HH:mm");
		String formattedStart = timeStart.format(formatter);
		String formattedEnd = timeFinish.format(formatter);
		this.timeStartFormat = formattedStart;
        this.timeFinishFormat = formattedEnd;
        this.title = title;
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

	public String getTimeStartFormat() {
		return timeStartFormat;
	}

	public void setTimeStartFormat(String timeStartFormat) {
		this.timeStartFormat = timeStartFormat;
	}

    public String getTimeFinishFormat() {
		return timeFinishFormat;
	}

	public void setTimeFinishFormat(String timeFinishFormat) {
		this.timeFinishFormat = timeFinishFormat;
	}

    public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void addComments(Comment comment){
		this.comments.add(comment);
	}

    public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

}
