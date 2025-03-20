package services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.grupo13.ssddgrupo13.entities.Comment;
import es.grupo13.ssddgrupo13.repository.CommentRepository;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Optional<Comment> findById(long id){
        return commentRepository.findById(id);
    }
    public void deleteById(long id){
        commentRepository.deleteById(id);
    }

}
