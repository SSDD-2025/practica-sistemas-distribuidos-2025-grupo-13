package es.grupo13.ssddgrupo13.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private float price;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String timeStartFormat;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    protected Ticket() {

    }

    public Ticket(String title, float price, LocalDateTime createdAt, TicketStatus status) {
        super();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy, HH:mm");
		String formattedStart = createdAt.format(formatter);
        this.timeStartFormat = formattedStart;
        this.title = title;
        this.price = price;
        this.createdAt = createdAt;
        this.status = status;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public String getTimeStartFormat() {
		return timeStartFormat;
	}

	public void setTimeStartFormat(String timeStartFormat) {
		this.timeStartFormat = timeStartFormat;
	}

}
