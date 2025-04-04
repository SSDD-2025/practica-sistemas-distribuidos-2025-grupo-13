package es.grupo13.ssddgrupo13.services;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.grupo13.ssddgrupo13.dto.CommentDTO;
import es.grupo13.ssddgrupo13.dto.CommentMapper;
import es.grupo13.ssddgrupo13.model.Client;
import es.grupo13.ssddgrupo13.model.Comment;
import es.grupo13.ssddgrupo13.repository.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private EventService eventService;

    @Autowired
    private ClientService clientService;
    
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private  CommentMapper commentMapper;

    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public Iterable<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment delete(Comment comment) {
        commentRepository.delete(comment);
        return comment;
    }

    public CommentDTO getComment(Long id) {
        return commentMapper.ToDTO(commentRepository.findById(id).orElseThrow());
    }

    public Collection<CommentDTO> getAllComments() {
        return commentMapper.ToDTOs(commentRepository.findAll());
    }

    public CommentDTO createComment(CommentDTO commentDTO) {
        Comment comment = commentMapper.ToDomain(commentDTO);
        commentRepository.save(comment);
        return commentMapper.ToDTO(comment);
    }

    public CommentDTO replaceComment(Long id, CommentDTO updatedCommentDTO) {
		if (commentRepository.existsById(id)) {
			Comment updatedComment = toDomain(updatedCommentDTO);
			updatedComment.setId(id);
			commentRepository.save(updatedComment);
			return toDTO(updatedComment);
		} else {
			throw new NoSuchElementException();
		}
	}

    public CommentDTO deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow();
        detachAndDelete(id);
        return commentMapper.ToDTO(comment);
    }

    @Transactional
    public void detachAndDelete(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        Client client = comment.getClient();
        client.getComments().remove(comment);
        clientService.save(client);
        
        if (comment.getEvent() != null) {
            comment.getEvent().getComments().remove(comment);
            eventService.save(comment.getEvent());
        }

        commentRepository.delete(comment);
    }

    public CommentDTO toDTO(Comment comment){
		return commentMapper.ToDTO(comment);
	}

	public Comment toDomain(CommentDTO commentDTO){
		return commentMapper.ToDomain(commentDTO);
	}

	public Collection<CommentDTO> toDTOs(Collection<Comment> comments){
		return commentMapper.ToDTOs(comments);
	}
}
