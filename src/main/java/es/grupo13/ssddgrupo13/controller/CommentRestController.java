package es.grupo13.ssddgrupo13.controller;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.grupo13.ssddgrupo13.dto.CommentDTO;
import es.grupo13.ssddgrupo13.repository.CommentRepository;
import es.grupo13.ssddgrupo13.services.CommentService;


@RestController
@RequestMapping("/api/comments")
public class CommentRestController {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;



    @GetMapping("/")
    public Page<CommentDTO> getAllComments(Pageable pageable) {
        return commentRepository.findAll(pageable).map(commentService::toDTO);
    }

    @GetMapping("/{id}")
    public CommentDTO getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    @PostMapping("/")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO) {
        CommentDTO commentDTOC = commentService.createComment(commentDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(commentDTOC.id()).toUri();
		return ResponseEntity.created(location).body(commentDTOC);
    }

    @DeleteMapping("/{id}")
    public CommentDTO deleteComment(@PathVariable long id) {
        return commentService.deleteComment(id);
    }
    
    
}
