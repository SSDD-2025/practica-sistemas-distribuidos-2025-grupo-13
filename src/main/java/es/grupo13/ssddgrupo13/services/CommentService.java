package es.grupo13.ssddgrupo13.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.grupo13.ssddgrupo13.model.Client;
import es.grupo13.ssddgrupo13.model.Comment;
import es.grupo13.ssddgrupo13.repository.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private EventService eventService;
    
    @Autowired
    private CommentRepository commentRepository;

    public Optional<Comment> findById(long id) {
        return commentRepository.findById(id);
    }

    public void deleteById(long id) {
        commentRepository.deleteById(id);
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

    @Transactional
    public void detachAndDelete(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null)
            return;
        Client client = comment.getClient();
        client.getComments().remove(comment);
        
        if (comment.getEvent() != null) {
            comment.getEvent().getComments().remove(comment);
            eventService.save(comment.getEvent());
        }

        commentRepository.delete(comment);
    }

}
