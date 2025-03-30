package es.grupo13.ssddgrupo13.dto;

public class CommentDTO {
    public record CommentDTOs(long id,String autor, String text, int rating, String title) {
    }
}
