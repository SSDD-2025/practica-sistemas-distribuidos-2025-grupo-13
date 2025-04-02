package es.grupo13.ssddgrupo13.controller;
import java.net.URI;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.grupo13.ssddgrupo13.dto.CommentDTO;
import es.grupo13.ssddgrupo13.services.CommentService;


@RestController
public class CommentRestController {

    @Autowired
    private CommentService commentService;

    @GetMapping(value = "/comments/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<CommentDTO> getAllComments() {
        return commentService.getComments();
    }
    @GetMapping("/comments/{id}")
    public CommentDTO getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id);
        }
    @PostMapping("/comments/")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO) {
        CommentDTO commentDTOC = commentService.createComment(commentDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(commentDTOC.id()).toUri();
		return ResponseEntity.created(location).body(commentDTOC);
        
    }
    @DeleteMapping("/comments/{id}")
    public CommentDTO deleteComment(@PathVariable long id) {
        return commentService.deleteComment(id);
    }
    
    
}
