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
    private String rating;

    protected Comment() {

    }

    public Comment(String autor, String text, int rating, String title) {
        super();
        this.autor = autor;
        this.text = text;
        String estrella = " &#9733";
        this.rating = "";  // Inicializamos la cadena
        for (int i = 0; i < rating; i++) {
            this.rating += estrella;  // Concatenamos correctamente
        }
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
