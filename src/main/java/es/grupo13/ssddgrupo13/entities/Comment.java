package es.grupo13.ssddgrupo13.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String autor;
    private String text;
    private int rating;

    protected Comment() {

    }

    public Comment(String autor ,String text, int rating, String title) {
        super();
        this.autor = autor;
        this.text = text;
        this.rating = rating;
        this.title = title;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    public String getAutor() {
		return autor;
	}

	public void setIAutor(String autor) {
        this.autor = autor;
		
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
