package es.grupo13.ssddgrupo13.entities;

import jakarta.persistence.*;

@Entity
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;
    private int rating;

    protected Comment() {

    }

    public Comment(String text, int rating) {
        super();
        this.text = text;
        this.rating = rating;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
